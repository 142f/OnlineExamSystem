<template>
	<div class="part">
		<div class="box" ref="box"></div>
		<div v-if="isNull">
			<span>该门作业还没人参考哦,请提醒学生参加作业。</span>
		</div>
	</div>
</template>

<!-- 以下为修改后的内容 todo -->
<!-- <template>
	<div id="part">
		<div ref="box" class="box"></div>
		<div class="notFound" v-if="isNull">
			<i class="iconfont icon-LC_icon_tips_fill"></i><span>该门作业还没人参考哦,请提醒学生参加作业</span>
		</div>
	</div>
</template> -->

<script>
	export default {
		name: "part",
		data() {
			return {
				isNull: false, //是否有成绩标志位
				name: null,
				category: { //保存分数段
					'成绩比例为90分及以上': 0,
					'成绩比例为80-89分': 0,
					'成绩比例为70-79分': 0,
					'成绩比例为60-69分': 0,
					'成绩比例为60分以下': 0,
				}
			}
		},
		created() {
			this.getScoreInfo()

		},
		methods: {
			getScoreInfo() {
				let examCode = this.$route.query.examCode
				this.name = this.$route.query.source
				this.$axios(`/api/scores/${examCode}`).then(res => {
					let data = res.data.data

					// if (data.length > 0) {   
					//以下为修改后  todo
					if (res.data.code == 200) {
						let box = this.$refs['box']
						let charts = this.$echarts.init(box)
						data.forEach(element => {
							// todo  element.etScore+element.dScore  满分没有100分如何
							// switch (element.etScore  / 10) {
						// console.log("------123")
						var etScore = Number(element.etScore) || 0; // 如果不是数字，则默认为 0  
						var dScore = Number(element.dScore) || 0; // 如果不是数字，则默认为 0  
						var score = Number(element.score) || 1; // 如果不是数字或 0，则默认为 1（避免除以 0）
						var result = (etScore + dScore) * 10 / score;  
						
						var result2 = Math.floor(result);  
						
						console.log("---result2");
						console.log(result2);
						
						// console.log(etScore)
						// console.log("---dScor")
						// console.log(dScore)
						// console.log(score)
						// console.log("---result")
						// console.log(result)
						//无法计算未nan (element.etScore + element.dScor) * 10 / element.score	
							switch (result2) {
								case 10:
								case 9:
									this.category["成绩比例为90分及以上"]++
									break
								case 8:
									this.category['成绩比例为80-89分']++
									break
								case 7:
									this.category["成绩比例为70-79分"]++
									break
								case 6:
									this.category['成绩比例为60-69分']++
									break
								default:
									this.category['成绩比例为60分以下']++
							}
						});
						let option = {
							title: {
								text: `${this.name}分数段图`,
								subtext: '分数段饼图',
								x: 'center'
							},
							tooltip: {
								trigger: 'item',
								formatter: "{a}：{b} <br/> {c}人 ({d}%)"
							},
							legend: {
								orient: 'vertical',
								left: 'left',
								data: ['成绩比例为90分及以上', '成绩比例为80-89分', '成绩比例为70-79分', '成绩比例为60-69分',
									'成绩比例为60分以下']
							},
							series: [{
								name: '分数段',
								type: 'pie',
								radius: '35%',
								center: ['50%', '35%'],
								data: [{
										// todo 比例值计算
										value: this.category['成绩比例为90分及以上'],
										name: '成绩比例为90分及以上'
									},
									{
										value: this.category['成绩比例为80-89分'],
										name: '成绩比例为80-89分'
									},
									{
										value: this.category['成绩比例为70-79分'],
										name: '成绩比例为70-79分'
									},
									{
										value: this.category['成绩比例为60-69分'],
										name: '成绩比例为60-69分'
									},
									{
										value: this.category['成绩比例为60分以下'],
										name: '成绩比例为60分以下'
									}
								],
								itemStyle: {
									emphasis: {
										shadowBlur: 10,
										shadowOffsetX: 0,
										shadowColor: 'rgba(0, 0, 0, 0.5)'
									}
								}
							}]
						};
						charts.setOption(option)
					} else {
						this.isNull = true;
					}
				})
			}
		},

	}
</script>

<style lang="less" scoped>
	.part {
		.box {
			width: 800px;
			height: 800px;
			margin-left: 40px;
		}

		.notFound {
			position: absolute;
			top: 0px;
			left: 0px;
		}
	}
</style>