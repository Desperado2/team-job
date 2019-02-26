var index_comp = Vue.component('index-comp',{
    template:`
        <el-container>
         <el-header style="height: 150px">
            <div style="height: 80px;width: 100%;margin-top: 60px">
                    <el-row :gutter="20">
                        <el-col :span="4" style="text-align: center"><img :src="currUser.headUrl" style="width: 60px">
                        </el-col>
                        <el-col :span="20">
                            <div style="font: 20px blod" v-text="currUser.name"></div>
                            <br>
                            <div v-text="currUser.department+' | '+currUser.position"></div>
                        </el-col>
                    </el-row>
                </div>
        </el-header>

        <el-main class="index-el-main">
            <el-card class="box-card" style="margin-bottom: 24px">
                <div slot="header" class="clearfix">
                    <span>开发神器</span>
                </div>
                <el-row :gutter="20">
                    <el-col :span="6">
                        <div style="text-align: center">
                            <img src="./global/image/122.jpg">
                            <div>Github</div>
                        </div>
                    </el-col>
                    <el-col :span="6">
                        <div style="text-align: center">
                            <img src="./global/image/122.jpg">
                            <div>Github</div>
                        </div>
                    </el-col>
                    <el-col :span="6"></el-col>
                    <el-col :span="6"></el-col>
                </el-row>
                <hr style="border: none; height: 1px; color: rgb(232, 232, 232); background-color: rgb(232, 232, 232); margin-bottom: 24px;">
                <el-tabs v-model="activeName" @tab-click="handleClick" tab-position="left">
                    <el-tab-pane label="常用导航" name="first">
                        <el-row :gutter="20" class="index-el-row">
                            <el-col :span="6"><div class="grid-content bg-purple">Jenkins</div></el-col>
                            <el-col :span="6"><div class="grid-content bg-purple">GitLab</div></el-col>
                            <el-col :span="6"><div class="grid-content bg-purple">语雀文档</div></el-col>
                            <el-col :span="6"><div class="grid-content bg-purple">prd文档</div></el-col>
                        </el-row>
                        <el-row :gutter="20" class="index-el-row">
                            <el-col :span="6"><div class="grid-content bg-purple">Jira缺陷管理</div></el-col>
                            <el-col :span="6"><div class="grid-content bg-purple">Sonar代码质量管理</div></el-col>
                            <el-col :span="6"><div class="grid-content bg-purple">阿狸企业邮箱</div></el-col>
                            <el-col :span="6"><div class="grid-content bg-purple">Yearning Sql审计</div></el-col>
                        </el-row>
                        <el-row :gutter="20" class="index-el-row">
                            <el-col :span="6"><div class="grid-content bg-purple">项目发布单</div></el-col>
                            <el-col :span="6"><div class="grid-content bg-purple">消息中心文档</div></el-col>
                            <el-col :span="6"><div class="grid-content bg-purple">Nexus Repository</div></el-col>
                            <el-col :span="6"><div class="grid-content bg-purple">域名代理配置</div></el-col>
                        </el-row>
                        <el-row :gutter="20" class="index-el-row">
                            <el-col :span="6"><div class="grid-content bg-purple">索引服务</div></el-col>
                            <el-col :span="6"><div class="grid-content bg-purple"></div></el-col>
                            <el-col :span="6"><div class="grid-content bg-purple"></div></el-col>
                            <el-col :span="6"><div class="grid-content bg-purple"></div></el-col>
                        </el-row>
                    </el-tab-pane>
                    <el-tab-pane label="指南" name="second">
                        <el-row :gutter="20" class="index-el-row">
                            <el-col :span="6"><div class="grid-content bg-purple">入职必看</div></el-col>
                            <el-col :span="6"><div class="grid-content bg-purple">框架资料</div></el-col>
                            <el-col :span="6"><div class="grid-content bg-purple">VPN配置</div></el-col>
                            <el-col :span="6"><div class="grid-content bg-purple">开发环境搭建</div></el-col>
                        </el-row>
                    </el-tab-pane>
                    <el-tab-pane label="线上" name="third">
                        <el-row :gutter="20" class="index-el-row">
                            <el-col :span="6"><div class="grid-content bg-purple">Jenkins</div></el-col>
                            <el-col :span="6"><div class="grid-content bg-purple">线上数据库</div></el-col>
                            <el-col :span="6"><div class="grid-content bg-purple">dubbo-admin</div></el-col>
                            <el-col :span="6"><div class="grid-content bg-purple">线上全链路日志</div></el-col>
                        </el-row>
                        <el-row :gutter="20" class="index-el-row">
                            <el-col :span="6"><div class="grid-content bg-purple">同一工作台</div></el-col>
                            <el-col :span="6"><div class="grid-content bg-purple"></div></el-col>
                            <el-col :span="6"><div class="grid-content bg-purple"></div></el-col>
                            <el-col :span="6"><div class="grid-content bg-purple"></div></el-col>
                        </el-row>
                    </el-tab-pane>
                    <el-tab-pane label="其他" name="fourth">其他</el-tab-pane>
                </el-tabs>
            </el-card>

            <el-card class="box-card">
                <div slot="header" class="clearfix">
                    <el-radio-group v-model="selectItem" :change="selectedItem(selectItem)">
                      <el-radio-button label="代码提交量"></el-radio-button>
                      <el-radio-button label="提交次数"></el-radio-button>
                    </el-radio-group>
                   <div style="float: right">
                       <el-date-picker
                          v-model="currWeek"
                          type="week"
                          format="yyyy 第 WW 周"
                          placeholder="选择周">
                        </el-date-picker>
                    </div>
                </div>
                
                <div id="echart" style="width: 100%;height:400px;"></div>
            </el-card>

        </el-main>
        </el-container>
    `,
    data(){
        return{
            activeName: 'first',
            currUser:{
                id:'',
                name: '',
                email: '',
                password: '',
                phone : '',
                birthday: '',
                birthType: '',
                department: '',
                position: '',
                headUrl:''
            },
            selectItem: '代码提交量',
            currWeek:''
        }
    },
    methods:{
        handleClick(tab, event) {
            console.log(tab, event);
        },
        selectedItem:function(item){
            if(item === '代码提交量'){
                if(this.app_onlyme.length === 0){
                    this.findDataByUserId();
                }
                this.allApps = this.app_onlyme
            }else{
                if(this.app_all.length === 0){
                    this.findData()
                }
                this.allApps = this.app_all
            }
        },
        drawLine:function () {
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('echart'));
            // 指定图表的配置项和数据
            myChart.setOption({
                tooltip: {
                    axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                        type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                    }
                },
                xAxis: {
                    data: ["张三", "李四", "王五", "王小虎", "李晓明", "Jack"]
                },
                yAxis: {
                    axisLine: {       //y轴
                        show: false
                    },
                    axisTick: {       //y轴刻度线
                        show: false
                    },
                },
                series: [{
                    name: '代码量',
                    type: 'bar',
                    data: [5, 20, 36, 10, 10, 20]
                }]
            });
        }
    },
    created:function(){
        let _this = this;
        axios({
            method: 'get',
            url: 'users/user',
        }).then(function (result) {
            if (result.data.success){
                _this.currUser = result.data.data;
            }else {
                _this.$message({
                    message:result.data.msg,
                    type:'error'
                });
            }
        });

        axios({
            method: 'get',
            url: 'users/user',
        }).then(function (result) {
            if (result.data.success){
                _this.currUser = result.data.data;
            }else {
                _this.$message({
                    message:result.data.msg,
                    type:'error'
                });
            }
        });
    },
    mounted: function () {
        this.drawLine();
    },
})