package com.hzl.hadoop.ics.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.ics.dto.IcsQuestionDTO;
import com.hzl.hadoop.ics.ee.SplitType;
import com.hzl.hadoop.ics.engine.SplitWord;
import com.hzl.hadoop.ics.entity.IcsKeyWordEntity;
import com.hzl.hadoop.ics.entity.IcsQuestionEntity;
import com.hzl.hadoop.ics.mapper.IcsQuestionMapper;
import com.hzl.hadoop.ics.service.IcsKeyWordService;
import com.hzl.hadoop.ics.service.IcsQuestionService;
import com.hzl.hadoop.ics.vo.IcsQuestionVO;
import org.ansj.domain.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


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
		for (char word : allWords) {
			insertKeyword(icsQuestionEntity.getId(), String.valueOf(word));
		}

		result.getTerms().forEach(term -> {
			insertKeyword(icsQuestionEntity.getId(), term.getRealName());


		});

		return true;
	}

	@Override
	public void searchQuestion(String question) {

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