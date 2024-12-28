package com.hzl.hadoop.gp.service.impl;

import com.hzl.hadoop.gp.service.GpTradingService;
import com.hzl.hadoop.gp.vo.Buying;
import com.hzl.hadoop.gp.vo.Selling;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class GpTradingServiceImpl implements GpTradingService {
	private BigDecimal buyingCost(BigDecimal buyingPrice, BigDecimal buyingNumber){
		//买入价格
		BigDecimal buyingAmount = buyingPrice.multiply(buyingNumber);
		//买入佣金
		BigDecimal buyingCommission= buyingAmount.multiply(new BigDecimal("0.0003"));
		log.info("买入佣金为：{}",buyingCommission);
		//买入总成本
		BigDecimal buyingTotalCost = buyingAmount.add(buyingCommission);
		return buyingTotalCost;
	}

	private BigDecimal sellingCost(BigDecimal sellingPrice, BigDecimal sellingNumber){
		//卖出价格
		BigDecimal sellingAmount = sellingPrice.multiply(sellingNumber);
		//卖出佣金
		BigDecimal sellingCommission = sellingAmount.multiply(new BigDecimal("0.0003"));
		log.info("卖出佣金为：{}",sellingCommission);
		///印花税0.5%
		BigDecimal sellingTax = sellingAmount.multiply(new BigDecimal("0.0005"));
		log.info("印花税为：{}",sellingTax);
		//卖出总成本
		BigDecimal sellingTotalCost = sellingAmount.subtract(sellingCommission).subtract(sellingTax);
		return sellingTotalCost;
	}
	@Override
	public BigDecimal calculateProfit(BigDecimal buyingPrice, BigDecimal buyingNumber, BigDecimal sellingPrice, BigDecimal sellingNumber) {
		//手续费（佣金）0.0002852=0.02852% 按照0.03%计算（买入卖出都需要）
		//印花税0.5%(只有卖出需要)
		//计算利润
		//买入总成本
		BigDecimal buyingTotalCost =buyingCost(buyingPrice, buyingNumber);
		log.info("买入总成本为：{}",buyingTotalCost);

		//卖出总成本
		BigDecimal sellingTotalCost =sellingCost(sellingPrice, sellingNumber);
		log.info("卖出总成本为：{}",sellingTotalCost);

		//利润
		BigDecimal profit = sellingTotalCost.subtract(buyingTotalCost);
		log.info("利润为：{}",profit);
		return profit;
	}

	//利润是通过不断买入卖出降低买入成本获取利润，股票数量尽量保持不变的情况下（最好的情况是股票数量不变，利润增加）
	//卖出的时候，等量卖出，或者数量由少变多
	//买入的时候，等量买入，或者数量由少变多
	//最小单位100股买入卖出，倍数买入卖出
	//卖出一定要高于买入价格，也就是要找一条低于买入价格的股票抵扣买入成本
	@Override
	public void trading() {
		//目标利润
		BigDecimal targetProfit = new BigDecimal("100");

      	//本金
		BigDecimal capital = new BigDecimal("112626.22");

		List<Buying> buyingList = new ArrayList<>();

		//买入股票数量
		BigDecimal buyingNumber = new BigDecimal("1500");
		//买入价格
		BigDecimal buyingPrice = new BigDecimal("30.98");
		Buying buying = new Buying(buyingNumber,buyingPrice);
		buyingList.add(buying);

		//买入成本
		BigDecimal buyingCost =buyingList.stream().map(a -> buyingCost(a.getBuyingPrice(), a.getBuyingNumber())).reduce(new BigDecimal("0"),BigDecimal::add);
		log.info("买入成本为：{}",buyingCost);

		//今日可卖出股票数量
		BigDecimal todaySellingNumber = new BigDecimal("1500");

		//持仓数量
		BigDecimal holdingNumber = new BigDecimal("1500");
		//当前股价
		BigDecimal currentPrice = new BigDecimal("31");

		//当前持仓金额
		BigDecimal holdingAmount = sellingCost(currentPrice, holdingNumber);

		//可用资金
		BigDecimal availableCapital = capital.subtract(buyingCost);


		//核心计算逻辑
		//目标卖出总价格
		BigDecimal targetSellingPrice = buyingCost.add(targetProfit);


		//当前价低基于开盘是涨，还是跌


	}

	public void trade(){
		//买入记录
		List<Buying> buyingList = new ArrayList<>();
		//当前可卖
		List<Buying> todayCanSelling = new ArrayList<>();

		//上涨预测未来价格
		List<BigDecimal> futurePrice = new ArrayList<>();

		//卖出记录
		List<Selling> sellingList = new ArrayList<>();


		//今日可卖
		BigDecimal todaySellingNumber = new BigDecimal("1500");



	}


	public static void main(String[] args) {
		GpTradingServiceImpl trading = new GpTradingServiceImpl();
		BigDecimal p1= trading.calculateProfit(new BigDecimal("30.85"), new BigDecimal("900"), new BigDecimal("30.98"), new BigDecimal("900"));
		BigDecimal p2= trading.calculateProfit(new BigDecimal("30.84"), new BigDecimal("400"), new BigDecimal("30.9"), new BigDecimal("400"));
		BigDecimal p3= trading.calculateProfit(new BigDecimal("31.03"), new BigDecimal("700"), new BigDecimal("30.9"), new BigDecimal("700"));

		log.info(p1.add(p2).add(p3).toString());
		//trading.trading();
	}
}
