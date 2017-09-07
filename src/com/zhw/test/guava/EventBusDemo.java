package com.zhw.test.guava;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;

import com.google.common.collect.Lists;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public class EventBusDemo {
	
	static EventBus eventBus = new EventBus();
	//或者
	//EventBus eventBus = new EventBus(TradeAccountEvent.class.getName());//带标识符,用于日志记录
	
	//闻其名，就是异步事件总线，当处理耗时的处理时很有用，我们要依赖Executors来实现异步事件总线
	private Executor executorService;
	AsyncEventBus asyncEventBus = new AsyncEventBus(executorService);
	
	
	public static void main(String[] args)  {
		
	}
	//测试用例
	public static void test()  {

		// 发布者,一个线程
		SimpleTradeExecutor tradeExecutor = new SimpleTradeExecutor(eventBus);
		tradeExecutor.executeTrade(new TradeAccount(), 1000, TradeType.SELL);
		tradeExecutor.executeTrade(new TradeAccount(), 2000, TradeType.BUY);
		
		// 订阅者，另外一个线程
		AllTradesAuditor auditor = new AllTradesAuditor(eventBus);
		auditor.unregister();
	}

	/**
	 * 事件类
	 */
	public static class TradeAccountEvent {
		private double amount;
		private Date tradeExecutionTime;
		private TradeType tradeType;
		private TradeAccount tradeAccount;

		public TradeAccountEvent(TradeAccount account, double amount,
				Date tradeExecutionTime, TradeType tradeType) {
			this.amount = amount;
			this.tradeExecutionTime = tradeExecutionTime;
			this.tradeAccount = account;
			this.tradeType = tradeType;
		}
	}

	/**
	 * 购买事件
	 */
	public static class BuyEvent extends TradeAccountEvent {
		public BuyEvent(TradeAccount tradeAccount, double amount,
				Date tradExecutionTime) {
			super(tradeAccount, amount, tradExecutionTime, TradeType.BUY);
		}
	}

	/**
	 * 卖出事件
	 */
	public static class SellEvent extends TradeAccountEvent {
		public SellEvent(TradeAccount tradeAccount, double amount,
				Date tradExecutionTime) {
			super(tradeAccount, amount, tradExecutionTime, TradeType.SELL);
		}
	}
	
	/**
	 * 卖出和购买审计，即订阅者
	 */
	public static class AllTradesAuditor {
		private List<BuyEvent> buyEvents = Lists.newArrayList();
		private List<SellEvent> sellEvents = Lists.newArrayList();
		EventBus eventBus;
		public AllTradesAuditor(EventBus eventBus) {
			eventBus.register(this);
			this.eventBus = eventBus;
		}

		/**
		 * 订阅卖出事件
		 */
		@Subscribe
		public void auditSell(SellEvent sellEvent) {
			sellEvents.add(sellEvent);
			System.out.println("Received TradeSellEvent " + sellEvent);
		}

		/**
		 * 订阅购买事件
		 */
		@Subscribe
		public void auditBuy(BuyEvent buyEvent) {
			buyEvents.add(buyEvent);
			System.out.println("Received TradeBuyEvent " + buyEvent);
		}
		
		//订阅者来取消注册
		public void unregister(){
		      this.eventBus.unregister(this);
		}
	}
	
	/**
	 * 执行交易, 即发布者
	 */
	public static class SimpleTradeExecutor {
		private EventBus eventBus;

		public SimpleTradeExecutor(EventBus eventBus) {
			this.eventBus = eventBus;
		}

		/**
		 * 执行交易
		 */
		public void executeTrade(TradeAccount tradeAccount, double amount,
				TradeType tradeType) {
			TradeAccountEvent tradeAccountEvent = processTrade(tradeAccount,
					amount, tradeType);
			eventBus.post(tradeAccountEvent); // 发布事件
		}

		/**
		 * 处理交易
		 * 
		 * @return 交易事件
		 */
		private TradeAccountEvent processTrade(TradeAccount tradeAccount,
				double amount, TradeType tradeType) {
			Date executionTime = new Date();
			String message = String.format(
					"Processed trade for %s of amount %n type %s @ %s",
					tradeAccount, amount, tradeType, executionTime);
			TradeAccountEvent tradeAccountEvent;
			if (tradeType.equals(TradeType.BUY)) { //购买动作
				tradeAccountEvent = new BuyEvent(tradeAccount, amount,
						executionTime);
			} else { //卖出动作
				tradeAccountEvent = new SellEvent(tradeAccount, amount,
						executionTime);
			}
			System.out.println(message);
			return tradeAccountEvent;
		}
	}
	
	public static class TradeType {
		int type = 0;
		public TradeType(int type){
			this.type = type;
		}
		public static final TradeType SELL = new TradeType(0);
		public static final TradeType BUY = new TradeType(1);
		
	}
	public static class TradeAccount {
		
	}
}
