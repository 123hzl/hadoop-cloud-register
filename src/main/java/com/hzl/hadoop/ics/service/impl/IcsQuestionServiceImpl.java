package com.hzl.hadoop.ics.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.exception.CommonException;
import com.hzl.hadoop.ics.constant.SplitType;
import com.hzl.hadoop.ics.dto.IcsQuestionDTO;
import com.hzl.hadoop.ics.engine.SplitWord;
import com.hzl.hadoop.ics.entity.IcsKeyWordEntity;
import com.hzl.hadoop.ics.entity.IcsQuestionEntity;
import com.hzl.hadoop.ics.entity.IcsQuestionSearchLogEntity;
import com.hzl.hadoop.ics.entity.IcsQuestionSulotionEntity;
import com.hzl.hadoop.ics.mapper.IcsQuestionMapper;
import com.hzl.hadoop.ics.service.IcsKeyWordService;
import com.hzl.hadoop.ics.service.IcsQuestionSearchLogService;
import com.hzl.hadoop.ics.service.IcsQuestionService;
import com.hzl.hadoop.ics.service.IcsQuestionSulotionService;
import com.hzl.hadoop.ics.vo.IcsQuestionVO;
import com.hzl.hadoop.ics.vo.IcsResultVO;
import org.ansj.domain.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service("icsQuestionService")
public class IcsQuestionServiceImpl extends ServiceImpl<IcsQuestionMapper, IcsQuestionEntity> implements IcsQuestionService {

	@Autowired
	IcsQuestionMapper mapper;
	@Autowired
	IcsKeyWordService icsKeyWordService;
	@Autowired
	IcsQuestionSearchLogService icsQuestionSearchLogService;
	@Autowired
	IcsQuestionSulotionService icsQuestionSulotionService;

	@Override
	public PageInfo<IcsQuestionDTO> queryPage(IcsQuestionVO params, Integer current, Integer pageSize) {
		PageInfo<IcsQuestionDTO> pageResult = PageHelper.startPage(current, pageSize).doSelectPageInfo(() -> mapper.listPage(params));

		return pageResult;
	}

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public boolean saveInfo(IcsQuestionEntity icsQuestionEntity) {
		//获取问题进行分词
		mapper.insert(icsQuestionEntity);

		SplitWord splitWord = new SplitWord();
		//todo 封装成自己的返回体，这样更换分词器的时候可以很方便
		Result result = splitWord.keyword(icsQuestionEntity.getQuestion());

		Set<String> keywords = result.getTerms().stream().map(term -> term.getRealName()).collect(Collectors.toSet());
		char[] allWords = icsQuestionEntity.getQuestion().toCharArray();
		for (char word : allWords) {
			keywords.add(String.valueOf(word));
		}

		//todo 优化成一次插入
		keywords.forEach(a -> {
			insertKeyword(icsQuestionEntity.getId(), a);
		});

		return true;
	}

	@Override
	public List<IcsResultVO> searchQuestion(String question) {
		if (!StringUtils.hasLength(question)) {
			throw new CommonException("问题不能为空！");
		}
		//插入搜索记录
		IcsQuestionSearchLogEntity icsQuestionSearchLogEntity = IcsQuestionSearchLogEntity.builder()
				.question(question)
				.isHit(false)
				.build();
		icsQuestionSearchLogService.save(icsQuestionSearchLogEntity);

		SplitWord splitWord = new SplitWord();
		//todo 封装成自己的返回体，这样更换分词器的时候可以很方便
		Result result = splitWord.keyword(question);

		Set<String> keywords = result.getTerms().stream().map(term -> term.getRealName()).collect(Collectors.toSet());
		char[] allWords = question.toCharArray();
		//todo 待优化
		for (char word : allWords) {
			keywords.add(String.valueOf(word));
		}

		List<IcsResultVO> resultVOS = mapper.searchQuestion(keywords);
		//插入问题搜索记录回答表 todo 改成异步操作
		resultVOS.forEach(a -> {
			icsQuestionSulotionService.save(IcsQuestionSulotionEntity.builder()
					.searchLogId(icsQuestionSearchLogEntity.getId())
					.icsQuestionId(a.getIcsQuestionId())
					.build());
		});

		return resultVOS;
	}

	public boolean insertKeyword(Long icsQuestionI, String key) {
		IcsKeyWordEntity icsKeyWordEntity = new IcsKeyWordEntity();
		icsKeyWordEntity.setKey(key);
		icsKeyWordEntity.setIcsQuestionId(icsQuestionI);
		icsKeyWordEntity.setKeyWordMachine(SplitType.ANSJ.value());
		//todo 后期定时把问题同步到elasticsearch
		return icsKeyWordService.save(icsKeyWordEntity);
	}

}