<!--考生答题界面-->
<template>
	<div id="answer">
		<!--顶部信息栏-->
		<div class="top">
			<ul class="item">
				<li class="item-top">
					<i class="iconfont icon-menufold icon20" ref="toggle" @click="slider_flag = !slider_flag"></i>
					<i class="iconfont icon-r-paper" style="font-size: 32px;">
					</i>
					{{ examData.type }}-{{ examData.source }}
				</li>
				<li @click="flag = !flag" style="cursor: pointer">
					<i class="iconfont icon-user icon20"></i>
					<div class="msg" v-if="flag" @click="flag = !flag">
						<p>姓名：{{ userInfo.name }}</p>
						<p>准考证号: {{ userInfo.id }}</p>
					</div>
				</li>
				<li><i class="iconfont icon-arrLeft icon20"></i></li>
			</ul>
		</div>
		<div class="flexarea">


			<!--左边题目编号区todo-->
			<transition name="slider-fade">
				<div class="left" v-if="slider_flag">
					<ul class="l-top">
						<li>
							<a href="javascript:;"></a>
							<span>当前</span>
						</li>
						<li>
							<a href="javascript:;"></a>
							<span>未答</span>
						</li>
						<li>
							<a href="javascript:;"></a>
							<span>已答</span>
						</li>
						<li>
							<a href="javascript:;"></a>
							<span>标记</span>
						</li>
					</ul>

					<div class="l-bottom">
						<!-- 大 -->
						<div class="item">
							<p>选择题部分</p>
							<ul>
								<li v-for="(list, index1) in topic[1]" :key="index1">
									<a href="javascript:;" @click="change(index1)" :class="{
                                        border:
                                            index == index1 &&
                                            currentType == 1,
                                        bg:
                                            bg_flag &&
                                            topic[1][index1].isClick ==
                                            true,
                                    }">
										<span :class="{
                                            mark:
                                                topic[1][index1].isMark ==
                                                true,
                                        }"></span>
										{{ index1 + 1 }}
									</a>
								</li>
							</ul>
						</div>

						<div class="item">
							<p>填空题部分</p>
							<ul>
								<li v-for="(list, index2) in topic[2]" :key="index2">
									<a href="javascript:;" @click="fill(index2)" :class="{
                                        border:
                                            index == index2 &&
                                            currentType == 2,
                                    bg: fillAnswer[index2][3] == true,
						
                                    }"><span :class="{
                                        mark:
                                            topic[2][index2].isMark ==
                                            true,
                                    }"></span>{{ topicCount[0] + index2 + 1 }}
									</a>
								</li>
							</ul>
						</div>


						<div class="item">
							<p>判断题部分</p>
							<ul>
								<li v-for="(list, index3) in topic[3]" :key="index3">
									<a href="javascript:;" @click="judge(index3)" :class="{
                                        border:
                                            index == index3 &&
                                            currentType == 3,
                                        bg:
                                            bg_flag &&
                                            topic[3][index3].isClick ==
                                            true,
                                    }"><span :class="{
                                            mark:
                                                topic[3][index3].isMark ==
                                                true,
                                        }"></span>{{
                                            topicCount[0] +
                                            topicCount[1] +
                                            index3 +
                                            1
                                        }}</a>
								</li>
							</ul>
						</div>

						<!-- todo 主观题部分  没有保存答题部分-->
						<div class="item">
							<p>主观题部分</p>
							<ul>
								<!-- todobag -->
								<li v-for="(list, index4) in topic[4]" :key="index4">
									<a href="javascript:;" @click="fill2(index4)" :class="{
						                border:
						                    index == index4 &&
						                    currentType == 4,
									
						                bg: fillAnswer2[index4][3] == true,
						            }"><span :class="{
						                mark:
						                    topic[4][index4].isMark ==
						                    true,
						            }"></span>{{
						                    topicCount[0] +
						                    topicCount[1] +
											topicCount[2]+
						                    index4 +
						                    1
						                }}</a>
								</li>
							</ul>
						</div>

						<!-- todo -->
						<el-button type="primary" style="width: 100%;font-size: 22px;margin: 25px 0 0 0;"
							@click="finishExam()"> 结束作业</el-button>
					</div>
				</div>
			</transition>


			<!--右边选择答题区-->
			<transition name="slider-fade">
				<div class="right">
					<div class="title">
						<p style="font-size: 22px;"><i class="iconfont icon-r-edit" style="font-size: 28px;"></i>
							{{ title }}
						</p>
						<i class="iconfont icon-right auto-right"></i>
						<span>全卷共{{
                            topicCount[0] + topicCount[1] + topicCount[2] + topicCount[3]
							
                        }}题{{ userInfo.id }}
							<span v-if="!isPractice">
								<i class="iconfont icon-time"></i>倒计时：<b>{{
                                    time
                                }}</b>分钟</span>
						</span>
					</div>
					<div class="content">
						<p class="topic">
							<span class="number">{{ number }}</span>{{ showQuestion }}
						</p>



						<div v-if="currentType == 1">
							<el-radio-group v-model="radio[index]" @change="getChangeLabel">
								<el-radio :label="1">{{
                                    showAnswer.answerA
                                }}</el-radio>
								<el-radio :label="2">{{
                                    showAnswer.answerB
                                }}</el-radio>
								<el-radio :label="3">{{
                                    showAnswer.answerC
                                }}</el-radio>
								<el-radio :label="4">{{
                                    showAnswer.answerD
                                }}</el-radio>
							</el-radio-group>
							<div class="analysis" v-if="isPractice && radio[index] != undefined">
								<ul>
									<li>
										<el-tag type="success">正确答案：</el-tag><span class="right">{{
                                            reduceAnswer.rightAnswer
                                        }}</span>
									</li>
									<li><el-tag>题目解析：</el-tag></li>
									<li>
										{{
                                            reduceAnswer.analysis == null
                                            ? "暂无解析"
                                            : reduceAnswer.analysis
                                        }}
									</li>
								</ul>
							</div>
						</div>

						<div class="fill" v-if="currentType == 2">
							<div v-for="(item, currentIndex) in part" :key="currentIndex">
								<el-input placeholder="请填在此处" v-model="fillAnswer[index][currentIndex]" clearable
									@blur="fillBG">
								</el-input>
							</div>
							<div class="analysis" v-if="
                                isPractice && fillAnswer[index][0] != undefined ">
								<ul>
									<li>
										<el-tag type="success">正确答案：</el-tag><span class="right">{{
                                            topic[2][index].answer
                                        }}</span>
									</li>
									<li><el-tag>题目解析：</el-tag></li>
									<li>
										{{
                                            topic[2][index].analysis == null
                                            ? "暂无解析"
                                            : topic[2][index].analysis
                                        }}
									</li>
								</ul>
							</div>
						</div>


						<!-- todo 填写格式 会同时变黑 解决-->
						<div class="fill2" v-if="currentType == 4">
							<div v-for="(item, currentIndex) in part" :key="currentIndex">
								<el-input placeholder="请填在此处" v-model="fillAnswer2[index][currentIndex]" clearable
									@blur="fillBG2">
								</el-input>
							</div>
							<div class="analysis" v-if="
						        isPractice && fillAnswer2[index][0] != undefined ">
								<ul>
									<li>
										<el-tag type="success">正确答案：</el-tag><span class="right">{{
						                    topic[4][index].answer
						                }}</span>
									</li>
									<li><el-tag>题目解析：</el-tag></li>
									<li>
										{{
						                    topic[4][index].analysis == null
						                    ? "暂无解析"
						                    : topic[4][index].analysis
						                }}
									</li>
								</ul>
							</div>
						</div>









						<div class="judge" v-if="currentType == 3">
							<el-radio-group v-model="judgeAnswer[index]" @change="getJudgeLabel"
								v-if="currentType == 3">
								<el-radio :label="1">正确</el-radio>
								<el-radio :label="2">错误</el-radio>
							</el-radio-group>
							<div class="analysis" v-if="
                                isPractice &&
                                judgeAnswer[index] != undefined
                            ">
								<ul>
									<li>
										<el-tag type="success">正确答案：</el-tag><span class="right">{{
                                            topic[3][index].answer == "T"
                                            ? "正确"
                                            : "错误"
                                        }}</span>
									</li>
									<li><el-tag>题目解析：</el-tag></li>
									<li>
										{{
                                            topic[3][index].analysis == null
                                            ? "暂无解析"
                                            : topic[3][index].analysis
                                        }}
									</li>
								</ul>
							</div>
						</div>
					</div>
					<div class="operation">
						<ul class="end">
							<li @click="previous()" style="font-size: 24px;">
								<i class="iconfont icon-r-left" style="font-size: 32px;margin-right: 5px;"></i><span>
									上一题</span>
							</li>
							<li @click="mark()">
								<span>标记</span>
							</li>
							<li @click="next()" style="font-size: 24px;">
								<span>下一题 </span><i class="iconfont icon-r-right"
									style="font-size: 32px;margin-left: 5px;"></i>
							</li>
						</ul>
					</div>
				</div>
			</transition>
		</div>
	</div>
</template>


<script>
	import {
		mapState
	} from "vuex";
	export default {
		data() {
			return {
				startTime: null, //作业开始时间
				endTime: null, //作业结束时间
				time: null, //作业持续时间
				reduceAnswer: [], //vue官方不支持3层以上数据嵌套,如嵌套则会数据渲染出现问题,此变量直接接收3层嵌套时的数据。
				answerScore: 0, //答题总分数
				bg_flag: false, //已答标识符,已答改变背景色
				isFillClick: false, //选择题是否点击标识符
				slider_flag: true, //左侧显示隐藏标识符
				flag: false, //个人信息显示隐藏标识符
				currentType: 1, //当前题型类型  1--选择题  2--填空题  3--判断题
				radio: [], //保存考生所有选择题的选项
				title: "请选择正确的选项",
				index: 0, //全局index
				userInfo: {
					//用户信息
					name: null,
					id: null,
				},
				topicCount: [], //每种类型题目的总数
				score: [], //每种类型分数的总数
				examData: {
					//作业信息
					// source: null,
					// totalScore: null,
				},
				topic: {
					//试卷信息
				},
				showQuestion: [], //当前显示题目信息
				showAnswer: {}, //当前题目对应的答案选项
				number: 1, //题号
				part: null, //填空题的空格数量

				fillAnswer: [
					[]
				], //二维数组保存所有填空题答案
				fillAnswer2: [
					[]
				], //二维数组保存所有大题答案

				judgeAnswer: [], //保存所有判断题答案
				topic1Answer: [], //学生选择题作答编号,
				rightAnswer: "",
				timer: "",
			};
		},
		created() {
			this.getCookies();
			this.getExamData();
			if (!this.isPractice) {
				this.showTime();
			}
		},

		methods: {
			getExamData() {
				//获取当前试卷所有信息
				let date = new Date();
				this.startTime = this.getTime(date);
				let examCode = this.$route.query.examCode; //获取路由传递过来的试卷编号
				this.$axios(`/api/exam/${examCode}`).then((res) => {
					//通过examCode请求试卷详细信息
					this.examData = {
						...res.data.data
					}; //获取作业详情
					this.index = 0;
					this.time = this.examData.totalTime; //获取分钟数
					let paperId = this.examData.paperId;
					this.$axios(`/api/paper/${paperId}`).then((res) => {
						//通过paperId获取试题题目信息
						this.topic = {
							...res.data
						};

						// todo
						let reduceAnswer = this.topic[1][this.index];
						this.reduceAnswer = reduceAnswer;
						let keys = Object.keys(this.topic); //对象转数组
						keys.forEach((e) => {
							let data = this.topic[e];
							this.topicCount.push(data.length);
							let currentScore = 0;
							for (let i = 0; i < data.length; i++) {
								//循环每种题型,计算出总分
								currentScore += data[i].score;
							}
							this.score.push(currentScore); //把每种题型总分存入score
						});
						let len = this.topicCount[1];
						let father = [];
						let father2 = [];
						for (let i = 0; i < len; i++) {
							//根据填空题数量创建二维空数组存放每道题答案
							let children = [null, null, null, null];
							father.push(children);

							let children2 = [null, null, null, null];
							father2.push(children2);
						}
						this.fillAnswer = father;
						this.fillAnswer2 = father2;
						let dataInit = this.topic[1];
						this.number = 1;
						this.showQuestion = dataInit[0].question;
						this.showAnswer = dataInit[0];
					});
				});
			},
			// todo 大题结束时返回数据库
			/* 	finishExam() {
					if (this.isPractice) {
						console.log("结束本次练习")
						// 练习交卷
						this.$confirm("结束本次练习？", "友情提示", {
								confirmButtonText: "立即交卷",
								cancelButtonText: "继续练习",
								type: "warning",
							})
							.then(() => {
								this.commit();
							})
							.catch(() => {});
					} else if (this.time > 0) {
						console.log(" 提前交卷1")
						// 提前交卷
						this.$confirm("作业结束时间未到,是否提前交卷", "友情提示", {
								confirmButtonText: "提交",
								cancelButtonText: "取消",
								type: "warning",
							})
							.then(() => {
								this.commit();
							})
							.catch(() => {});
					}
				},
				 */
			finishExam() {
				console.log("打印isPractice");
				console.log(this.isPractice);
				if (this.isPractice) {
					console.log("结束本次练习");
					this.$confirm("结束本次练习？", "友情提示", {
							confirmButtonText: "结束练习",
							cancelButtonText: "继续练习",
							type: "warning",
						})
						.then(() => {
							// 假设 commit 是一个 Vuex mutation 的调用  
							this.commit(); // 替换为实际的 mutation 名称  
						})
						.catch(error => {
							console.error('在结束练习时发生错误:', error); // 至少记录错误  
						});
				}
				console.log("打印time");
				console.log("time");
				if (this.time > 0) { // 使用更明确的变量名  
					console.log("提前交卷1");
					this.$confirm("作业结束时间未到,是否提前交卷", "友情提示", {
							confirmButtonText: "提交",
							cancelButtonText: "取消",
							type: "warning",
						})
						.then(() => {
							// 提交作业到服务器或进行其他操作  
							this.commit(); // 假设这是提交作业的方法  
						})
						.catch(error => {
							console.error('在提前交卷时发生错误:', error); // 至少记录错误  
						});
				}
			},


			getTime(date) {
				//日期格式化
				let year = date.getFullYear();
				let month =
					date.getMonth() + 1 < 10 ?
					"0" + (date.getMonth() + 1) :
					date.getMonth() + 1;
				let day =
					date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
				let hours =
					date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
				let minutes =
					date.getMinutes() < 10 ?
					"0" + date.getMinutes() :
					date.getMinutes();
				let seconds =
					date.getSeconds() < 10 ?
					"0" + date.getSeconds() :
					date.getSeconds();
				// 拼接
				return (
					year +
					"-" +
					month +
					"-" +
					day +
					" " +
					hours +
					":" +
					minutes +
					":" +
					seconds
				);
			},
			getCookies() {
				//获取cookie
				this.userInfo.name = this.$cookies.get("cname");
				this.userInfo.id = this.$cookies.get("cid");
			},
			calcuScore() {
				//计算答题分数
			},

			judge(index) {
				//判断题
				let len = this.topic[3].length;
				this.currentType = 3;
				this.index = index;
				if (this.index < len) {
					if (this.index < 0) {
						this.index = this.topic[2].length - 1;
						this.fill(this.index);
					} else {
						console.log(`总长度${len}`);
						console.log(`当前index:${this.index}`);
						this.title = "请作出正确判断";
						let Data = this.topic[3];
						console.log(Data);
						this.showQuestion = Data[index].question; //获取题目信息
						this.number =
							this.topicCount[0] + this.topicCount[1] + index + 1;
					}
				} else if (this.index >= len) {
					this.index = 0;
					this.change(this.index);
				}
			},
			change(index) {
				//选择题
				this.index = index;
				let reduceAnswer = this.topic[1][this.index];
				this.reduceAnswer = reduceAnswer;
				this.isFillClick = true;
				this.currentType = 1;
				let len = this.topic[1].length;
				if (this.index < len) {
					if (this.index <= 0) {
						this.index = 0;
					}
				
					let Data = this.topic[1];
					console.log(`-------选择题总长度${len}`);
					console.log(`-----当前index:${index}`);
					console.log(`-------选择题数据打印`);
					console.log(Data);
					
					this.title = "请选择正确的选项";
					console.log(Data)
					this.showQuestion = Data[this.index].question; //获取题目信息
					this.showAnswer = Data[this.index];
					this.number = this.index + 1;
				} else if (this.index >= len) {
					this.index = 0;
					this.fill(this.index);
				}
			},

			fill(index) {
				//填空题
				let len = this.topic[2].length;
				this.currentType = 2;
				this.index = index;
				if (index < len) {
					if (index < 0) {
						index = this.topic[1].length - 1;
						this.change(index);
					} else {
						this.title = "请在横线处填写答案";
						let Data = this.topic[2];
						console.log("-----填空题数据打印");
						console.log("-----Data");
						
						this.showQuestion = Data[index].question; //获取题目信息
						let part = this.showQuestion.split("()").length - 1; //根据题目中括号的数量确定填空横线数量
						this.part = part;
						this.number = this.topicCount[0] + index + 1;
					}
				} else if (index >= len) {
					this.index = 0;
					this.judge(this.index);
				}
			}, // todo fill2

			fill2(index) {
				//大题
				let len = this.topic[4].length;
				this.currentType = 4;
				this.index = index;
				if (index < len) {
					if (index < 0) {
						index = this.topic[3].length - 1;
						// todo有误
						this.change(index);
					} else {
						this.title = "请在横线处填写答案";
						let Data = this.topic[4];
						console.log(Data);
						console.log('-----------大题信息打印');
						
						console.log(`当前index:${index}`);
						console.log(`-------大题数据打印`);
						console.log(Data);			
						//todo 大题打印
						console.log('---------内容打印 this.fillAnswer2[this.index][0]');
						console.log(this.fillAnswer2[this.index][0])
						console.log('---------- this.topic[4][this.index].questionId 打印');
						console.log(this.topic[4][this.index].questionId);
						
						if (!this.isPractice) {
							console.log("大题 提交内容");
							this.$axios({
								url: '/api/daQuestionAnswer',
								method: 'post',
								data: {
									studentId: this.userInfo.id,
									questionId: this.topic[4][this.index].questionId,
									answerMe: this.fillAnswer2[this.index][0]
								}
							}).then(() => {}).catch(() => {});
						}
						//目前只是进去是传递，第一次为空 ，设置为离开时传递或者输入时 或者考试结束时todo
						// this.$axios(
						// 	`/api/daQuestionUpdate/${this.userInfo.id}
						// 	/${this.topic[4][this.index].questionId}/${this.fillAnswer2[this.index][0] }`
						// ).then(res => {

						// }).catch(error => {});
						// this.$axios(
						// 		`/api/daQuestionUpdate/${this.}/${this.pagination.size}/${this.user.userId }`
						// 	).then(res => {
						// 		this.pagination = res.data.data;
						// 		console.log(this.pagination)
						// 	}).catch(error => {});



						this.showQuestion = Data[index].question; //获取题目信息
						// let part = this.showQuestion.split("()").length - 1; //根据题目中括号的数量确定填空横线数量
						// this.part = part;
						this.part = 1;
						// todo </span>{{topicCount[0] +topicCount[1] +topicCount[2]+ index4 +  1  }}</a>
						// this.number = this.topicCount[0] + index + 1;
						this.number = this.topicCount[0] + this.topicCount[1] + this.topicCount[2] + index + 1;
					}
				} else if (index >= len) {
					this.index = 0;
					this.judge(this.index);
				}
			},
			fillBG() {
				//填空题已答题目 如果已答该题目,设置第四个元素为true为标识符  todo
				if (this.fillAnswer[this.index][0] != null) {
					// todo 当前题目大题内容
					console.log(this.fillAnswer[this.index][0])
					console.log('zzz')
					this.fillAnswer[this.index][3] = true;
				}
			},
			fillBG2() {
				//填空题已答题目 如果已答该题目,设置第四个元素为true为标识符  todo
				if (this.fillAnswer2[this.index][0] != null) {
					this.fillAnswer2[this.index][3] = true;
				}
			},


			getChangeLabel(val) {
				//获取选择题作答选项
				this.radio[this.index] = val; //当前选择的序号
				if (val) {
					let data = this.topic[1];
					this.bg_flag = true;
					data[this.index]["isClick"] = true;
				}
				/* 保存学生答题选项 */
				this.topic1Answer[this.index] = val;
			},
			getJudgeLabel(val) {
				//获取判断题作答选项
				this.judgeAnswer[this.index] = val;
				if (val) {
					let data = this.topic[3];
					this.bg_flag = true;
					data[this.index]["isClick"] = true;
				}
			},
			previous() {
				//上一题
				this.index--;
				switch (this.currentType) {
					case 1:
						this.change(this.index);
						break;
					case 2:
						this.fill(this.index);
						break;
					case 3:
						this.judge(this.index);
						break;
				}
			},
			next() {
				//下一题
				this.index++;
				switch (this.currentType) {
					case 1:
						this.change(this.index);
						break;
					case 2:
						this.fill(this.index);
						break;
					case 3:
						this.judge(this.index);
						break;
				}
			},
			mark() {
				//标记功能
				switch (this.currentType) {
					case 1:
						this.topic[1][this.index]["isMark"] = true; //选择题标记
						break;
					case 2:
						this.topic[2][this.index]["isMark"] = true; //填空题标记
						break;
					case 3:
						this.topic[3][this.index]["isMark"] = true; //判断题标记
					case 4:
						this.topic[4][this.index]["isMark"] = true; //判断题标记
				}
			},
			commit() {
				//答案提交计算分数
				/* 计算选择题总分 */
				let topic1Answer = this.topic1Answer;
				let finalScore = 0;
				// todo结束时加入数据库

				console.log("commit  打印isPractice");
				console.log(this.isPractice);

				console.log("打印 this.topic[4][this.index].questionId");
				console.log("1");
				console.log(this.topic[4][this.index].questionId);
				console.log("2");
				if (!this.isPractice) {
					console.log("commit里  打印isPractice");
					this.$axios({
						url: '/api/daQuestionAnswer',
						method: 'post',
						data: {
							studentId: this.userInfo.id,
							questionId: this.topic[4][this.index].questionId,
							answerMe: this.fillAnswer2[this.index][0]
						}
					}).then(() => {}).catch(() => {});
				}

				topic1Answer.forEach((element, index) => {
					//循环每道选择题根据选项计算分数
					let right = null;
					if (element != null) {
						switch (
							element //选项1,2,3,4 转换为 "A","B","C","D"
						) {
							case 1:
								right = "A";
								break;
							case 2:
								right = "B";
								break;
							case 3:
								right = "C";
								break;
							case 4:
								right = "D";
						}
						if (right == this.topic[1][index].rightAnswer) {
							// 当前选项与正确答案对比
							finalScore += this.topic[1][index].score; // 计算总分数
						}
					}
				});
				/**计算判断题总分 */
				let fillAnswer = this.fillAnswer;
				fillAnswer.forEach((element, index) => {
					//此处index和 this.index数据不一致，注意
					element.forEach((inner) => {
						if (this.topic[2][index].answer.includes(inner)) {
							//判断填空答案是否与数据库一致
							finalScore += this.topic[2][this.index].score;
						}
					});
				});
				/**计算大题总分  todo 需要人工判断*/
				// let fillAnswer2 = this.fillAnswer2;
				// fillAnswer2.forEach((element, index) => {
				// 	//此处index和 this.index数据不一致，注意
				// 	element.forEach((inner) => {
				// 		if (this.topic[4][index].answer.includes(inner)) {
				// 			//判断填空答案是否与数据库一致
				// 			finalScore += this.topic[4][this.index].score;
				// 		}
				// 	});
				// });

				/** 计算判断题总分 */
				let topic3Answer = this.judgeAnswer;
				topic3Answer.forEach((element, index) => {
					let right = null;
					switch (element) {
						case 1:
							right = "T";
							break;
						case 2:
							right = "F";
					}
					if (right == this.topic[3][index].answer) {
						// 当前选项与正确答案对比
						finalScore += this.topic[3][index].score; // 计算总分数
					}
				});
				// 提交分数到数据库
				let date = new Date();
				this.endTime = this.getTime(date);
				if (this.isPractice) {
					// 练习，不提交到数据库
					this.$router.push({
						path: "/studentScore",
						query: {
							score: finalScore,
							startTime: this.startTime,
							endTime: this.endTime,
						},
					});
					return;
				}
				let answerDate = this.endTime.substr(0, 10);
				//提交成绩信息
				this.$axios({
					url: "/api/score",
					method: "post",
					// todo 未计算大题部分
					data: {
						examCode: this.examData.examCode, //作业编号
						studentId: this.userInfo.id, //学号
						subject: this.examData.source, //课程名称
						ptScore: ((finalScore / this.examData.totalScore) * 100 >= 60) ? 1 : 0, //是否及格
						etScore: finalScore, //答题成绩
						score: this.examData.totalScore, //试卷总成绩
						answerDate: answerDate, //答题日期
					},
				}).then((res) => {
					if (res.data.code == 200) {
						this.$router.push({
							path: "/studentScore",
							query: {
								score: finalScore,
								startTime: this.startTime,
								endTime: this.endTime,
							},
						});
					}
				});
			},
			showTime() {
				//倒计时
				this.timer = setInterval(() => {
					this.time -= 1;
					if (this.time == 10) {
						this.$message({
							showClose: true,
							type: "error",
							message: "考生注意,作业时间还剩10分钟！！！",
						});
					}

					if (this.time <= 0) {
						this.$message({
							showClose: true,
							type: "error",
							message: "作业时间到，系统自动提交试卷",
						});
						this.commit();
					}
				}, 1000 * 60);
			},
		},
		beforeDestroy() {
			// 清除计时器
			clearInterval(this.timer);
		},
		computed: mapState(["isPractice"]),
	};
</script>




<style lang="less">
	.iconfont.icon-time {
		color: CornflowerBlue;
		margin: 0px 6px 0px 20px;
	}

	.analysis {
		margin-top: 20px;

		.right {
			color: #2776df;
			font-size: 18px;
			border: 1px solid #2776df;
			padding: 0px 6px;
			border-radius: 4px;
			margin-left: 20px;
		}

		ul li:nth-child(2) {
			margin: 20px 0px;
		}

		ul li:nth-child(3) {
			padding: 10px;
			background-color: #d3c6c9;
			border-radius: 4px;
		}
	}

	.analysis span:nth-child(1) {
		font-size: 18px;
	}

	.mark {
		position: absolute;
		width: 4px;
		height: 4px;
		content: "";
		background-color: red;
		border-radius: 50%;
		top: 0px;
		left: 22px;
	}

	.border {
		position: relative;
		border: 1px solid #ff90aa !important;
	}

	.bg {
		background-color: #5188b8 !important;
	}

	.fill .el-input {
		display: inline-flex;
		width: 150px;
		margin-left: 20px;

		.el-input__inner {
			border: 1px solid transparent;
			border-bottom: 1px solid #eee;
			padding-left: 20px;
		}
	}

	/* slider过渡效果 */
	.slider-fade-enter-active {
		transition: all 0.3s ease;
	}

	.slider-fade-leave-active {
		transition: all 0.3s cubic-bezier(1, 0.5, 0.8, 1);
	}

	.slider-fade-enter,
	.slider-fade-leave-to {
		transform: translateX(-100px);
		opacity: 0;
	}

	.operation .end li:nth-child(2) {
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
		background-color: CornflowerBlue;
		border-radius: 50%;
		width: 50px;
		height: 50px;
		color: #fff;
	}

	.operation .end li {
		cursor: pointer;
		margin: 0 100px;
	}

	.operation {
		background-color: #fff;
		border-radius: 4px;
		padding: 10px 0px;
		margin-right: 10px;
	}

	.item-top {
		font-size: 24px;
	}

	.operation .end {
		display: flex;
		justify-content: center;
		align-items: center;
		color: CornflowerBlue;
	}

	.content .number {
		display: inline-flex;
		justify-content: center;
		align-items: center;
		width: 20px;
		height: 20px;
		background-color: CornflowerBlue;
		border-radius: 4px;
		margin-right: 4px;
	}

	.content {
		padding: 0px 20px;
	}

	.content .topic {
		padding: 20px 0px;
		padding-top: 30px;
	}

	.right .content {
		background-color: #fff;
		margin: 10px;
		margin-left: 0px;
		height: 470px;
	}

	.content .el-radio-group label {
		color: #000;
		margin: 5px 0px;
	}

	.content .el-radio-group {
		display: flex;
		flex-direction: column;
	}

	.right .title p {
		margin-left: 20px;
	}

	.flexarea {
		display: flex;
	}

	.flexarea .right {
		flex: 1;
	}

	.auto-right {
		margin-left: auto;
		color: #2776df;
		margin-right: 10px;
	}

	.right .title {
		margin-right: 10px;
		padding-right: 30px;
		display: flex;
		margin-top: 10px;
		background-color: #fff;
		height: 50px;
		line-height: 50px;
	}

	.clearfix {
		clear: both;
	}

	.l-bottom .final {
		cursor: pointer;
		display: inline-block;
		text-align: center;
		background-color: CornflowerBlue;
		width: 240px;
		margin: 20px 0px 20px 10px;
		border-radius: 4px;
		height: 30px;
		line-height: 30px;
		color: #fff;
		margin-top: 22px;
	}

	#answer .left .item {
		padding: 0px;
		font-size: 16px;
	}

	.l-bottom {
		border-radius: 4px;
		background-color: #fff;
	}

	.l-bottom .item p {
		margin-bottom: 15px;
		margin-top: 10px;
		color: #000;
		margin-left: 10px;
		letter-spacing: 2px;
	}

	.l-bottom .item li {
		width: 15%;
		margin-left: 5px;
		margin-bottom: 10px;
	}

	.l-bottom .item {
		display: flex;
		flex-direction: column;
	}

	.l-bottom .item ul {
		width: 100%;
		margin-bottom: -8px;
		display: flex;
		justify-content: space-around;
		flex-wrap: wrap;
	}

	.l-bottom .item ul li a {
		position: relative;
		justify-content: center;
		display: inline-flex;
		align-items: center;
		width: 30px;
		height: 30px;
		border-radius: 50%;
		background-color: #fff;
		border: 1px solid #eee;
		text-align: center;
		color: #000;
		font-size: 16px;
	}

	.left .l-top {
		display: flex;
		justify-content: space-around;
		padding: 16px 0px;
		border: 1px solid #eee;
		border-radius: 4px;
		margin-bottom: 10px;
		background-color: #fff;
	}

	.left {
		width: 260px;
		height: 100%;
		margin: 10px 10px 0px 10px;
	}

	.left .l-top li:nth-child(2) a {
		border: 1px solid #eee;
	}

	.left .l-top li:nth-child(3) a {
		background-color: #5188b8;
		border: none;
	}

	.left .l-top li:nth-child(4) a {
		position: relative;
		border: 1px solid #eee;
	}

	.left .l-top li:nth-child(4) a::before {
		width: 4px;
		height: 4px;
		content: " ";
		position: absolute;
		background-color: red;
		border-radius: 50%;
		top: 0px;
		left: 16px;
	}

	.left .l-top li {
		display: flex;
		justify-content: center;
		align-items: center;
		flex-direction: column;
	}

	.left .l-top li a {
		display: inline-block;
		padding: 10px;
		border-radius: 50%;
		background-color: #fff;
		border: 1px solid #ff90aa;
	}

	#answer .top {
		background-color: CornflowerBlue;
	}

	#answer .item {
		color: #fff;
		display: flex;
		padding: 20px;
		font-size: 20px;
	}

	#answer .top .item li:nth-child(1) {
		margin-right: 10px;
	}

	#answer .top .item li:nth-child(3) {
		position: relative;
		margin-left: auto;
	}

	#answer {
		padding-bottom: 30px;
	}

	.icon20 {
		font-size: 20px;
		font-weight: bold;
	}

	.item .msg {
		padding: 10px 15px;
		border-radius: 4px;
		top: 47px;
		right: -30px;
		color: #6c757d;
		position: absolute;
		border: 1px solid rgba(0, 0, 0, 0.15);
		background-color: #fff;
	}

	.item .msg p {
		font-size: 16px;
		width: 200px;
		text-align: left;
	}
</style>
<!-- 视觉统一：仅覆盖颜色/间距/圆角，不改动任何交互与结构 -->
<style lang="less">
	#answer {
		background: var(--oes-bg);
		min-height: 100vh;
	}

	#answer .top {
		background: linear-gradient(90deg, #2b4de0 0%, #3d7eff 100%);
		color: #fff;
		box-shadow: 0 2px 12px rgba(43, 77, 224, 0.28);
	}

	#answer .top .item li {
		color: #fff;
	}

	#answer .flexarea {
		background: var(--oes-bg);
	}

	#answer .left {
		background: #fff;
		border-right: 1px solid var(--oes-border);
		box-shadow: var(--oes-shadow-sm);
	}

	#answer .left .l-bottom .item p {
		color: var(--oes-text);
		font-weight: 600;
	}

	#answer .left .l-bottom ul li a {
		border-radius: 8px;
	}

	#answer .right {
		background: transparent;
	}

	#answer .content {
		background: #fff;
		border-radius: var(--oes-radius);
		box-shadow: var(--oes-shadow-sm);
		padding: 24px;
	}

	#answer .right .title {
		color: var(--oes-text);
		font-weight: 600;
	}

	#answer .analysis ul li:nth-child(3) {
		background: #f8fafd;
		color: var(--oes-text-secondary);
	}

	#answer .operation {
		background: #fff;
		border-top: 1px solid var(--oes-border);
		padding: 12px 24px;
	}

	#answer .operation .el-button {
		border-radius: 8px;
	}
</style>
