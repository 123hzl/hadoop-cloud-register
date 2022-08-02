package com.hzl.hadoop.ics.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.exception.CommonException;
import com.hzl.hadoop.ics.dto.IcsQuestionDTO;
import com.hzl.hadoop.ics.constant.SplitType;
import com.hzl.hadoop.ics.engine.SplitWord;
import com.hzl.hadoop.ics.entity.IcsKeyWordEntity;
import com.hzl.hadoop.ics.entity.IcsQuestionEntity;
import com.hzl.hadoop.ics.mapper.IcsQuestionMapper;
import com.hzl.hadoop.ics.service.IcsKeyWordService;
import com.hzl.hadoop.ics.service.IcsQuestionService;
import com.hzl.hadoop.ics.vo.IcsQuestionVO;
import com.hzl.hadoop.ics.vo.IcsResultVO;
import org.ansj.domain.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service("icsQuestionService")
public class IcsQuestionServiceImpl extends ServiceImpl<IcsQuestionMapper, IcsQuestionEntity> implements IcsQuestionService {

	@Autowired
	IcsQuestionMapper mapper;
	@Autowired
	IcsKeyWordService icsKeyWordService;

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

		char[] allWords = icsQuestionEntity.getQuestion().toCharArray();
		Set<String> wordsDis=new HashSet<>();
		for (char word : allWords) {
			wordsDis.add(String.valueOf(word).concat("-"));
		}
		wordsDis.forEach(a->{
			insertKeyword(icsQuestionEntity.getId(),a);
		});


		result.getTerms().forEach(term -> {
			insertKeyword(icsQuestionEntity.getId(), term.getRealName());

		});


		return true;
	}

	@Override
	public List<IcsResultVO> searchQuestion(String question) {
		if(!StringUtils.hasLength(question)){
			throw new CommonException("问题不能为空！");
		}
		SplitWord splitWord = new SplitWord();
		//todo 封装成自己的返回体，这样更换分词器的时候可以很方便
		Result result = splitWord.keyword(question);
		List<String> keywords=result.getTerms().stream().map(term ->term.getRealName()).collect(Collectors.toList());
		char[] allWords = question.toCharArray();
		//todo 待优化
		for(char word:allWords){
			keywords.add(String.valueOf(word).concat("-"));
		}

		return mapper.searchQuestion(keywords);
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