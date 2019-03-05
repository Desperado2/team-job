var week_report = Vue.component('week-report',{
    template:`
        <el-container>
          <el-header style="height: 120px">
            <div style="background-color: white;height: 140px;margin-top: 10px">
                <el-tabs v-model="activeName" @tab-click="handleClick" style="height: 70px;width: 100%;margin-top: 60px">
                    <el-tab-pane label="写周报" name="first"></el-tab-pane>
                    <el-tab-pane label="团队周报" name="second"></el-tab-pane>
                </el-tabs>
            </div>
        </el-header>
        <el-main class="index-el-main">
            <el-card class="box-card" style="padding: 0px 22%" v-if="activeName === 'first'">
                <el-form ref="weekly" :model="weekly" :rules="rules" label-width="100px">
                    <el-form-item label="本周工作:">
                        <el-row :gutter="20" style="border: 1px solid darkgray;margin-left: 0;margin-right: 0">
                            <el-col :span="6">
                                <el-button type="text" @click="importLastWeekReport"><i class="el-icon-edit"></i>导入上周周报</el-button>
                            </el-col>
                            <el-col :span="6">
                                <el-button type="text" @click="importLastWeekGitLogs"><i class="el-icon-edit"></i>导入Git记录</el-button>
                            </el-col>
                            <el-col :span="6">
                                <el-button type="text" disabled><i class="el-icon-edit"></i>导入会议记录</el-button>
                            </el-col>
                        </el-row>
                        <el-input type="textarea" rows="10" v-model="weekly.thisWeekReport"></el-input>
                    </el-form-item>
                    <el-form-item label="下周工作:">
                        <el-input type="textarea" rows="6" v-model="weekly.nextWeekReport"></el-input>
                    </el-form-item>
                    <el-form-item label="感想(选填):">
                        <el-input type="textarea" rows="4" v-model="weekly.feeling"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="submitForm('weekly')">提交</el-button>
                    </el-form-item>
                </el-form>
            </el-card>
            
          
            <el-card v-if="activeName === 'second'">
                    <div slot="header" class="clearfix">
                           <el-date-picker
                              v-model="currWeek"
                              type="week"
                              format="yyyy 第 W 周"
                              placeholder="选择周">
                            </el-date-picker>
                        </div>
                <div v-for="weekReport in weeklys" style="border-bottom: 1px solid #ebeef5;">
                    <el-row>
                          <el-col :span="4">
                                <div class="grid-content bg-purple">
                                    <img :src="weekReport.headurl"  style="width: 50px;border-radius: 50%">
                                    <div v-text="weekReport.username"></div>
                                </div>
                          </el-col>
                          <el-col :span="20">
                                <div class="grid-content bg-purple-light"style="text-align: left" v-if="weekReport.feeling !== null || weekReport.feeling !== ''">
                                    <div style="font-size: 24px;background-color: rgb(205, 220, 57);padding: 8px;font-weight: bold;color: white" v-text="weekReport.feeling"></div>
                                </div>
        
                                <el-row style="margin: 32px 0;">
                                    <el-col :span="12">
                                        <div class="grid-content bg-purple" style="text-align: left">
                                            <div>本周工作:</div><br>
                                            <div v-html="weekReport.thisWeekReport"></div>
                                        </div>
                                    </el-col>
                                    <el-col :span="12">
                                            <div class="grid-content bg-purple-light" style="text-align: left">
                                                <div>下周工作:</div><br>
                                                <div v-html="weekReport.nextWeekReport"></div>
                                            </div>
                                    </el-col>
                                </el-row>
                          </el-col>
                    </el-row>
                </div>
            </el-card>
        </el-main>
        </el-container>
    `,
    data(){
        return{
            activeName: 'first',
            currWeek:new Date(),
            weekly:{
                thisWeekReport:'',
                nextWeekReport:'',
                feeling:''
            },
            weeklys:[],
            rules: {
                thisWeekReport: [
                    {required: true, message: '请填写本周工作', trigger: 'blur'},
                ],
                nextWeekReport: [
                    {required: true, message: '请填写下周工作', trigger: 'blur'}
                ]
            }
        }
    },
    watch:{
        currWeek:function (newWeek,oldWeek) {
            if(newWeek !== oldWeek){
                let year = newWeek.getFullYear().toString();
                let month = newWeek.getMonth().toString();
                let day = newWeek.getDate().toString();
                let yearweek = year + this.getWeekStr(year,month,day)
                this.getWeeklys(yearweek)
            }
        },
    },
    mounted:function(){
        let newWeek = new Date();
        let year = newWeek.getFullYear().toString();
        let month = newWeek.getMonth().toString();
        let day = newWeek.getDate().toString();
        let yearweek = year + this.getWeekStr(year,month,day)
        this.getCurrWeekReport(yearweek)
    },
    methods:{
        handleClick:function (tab, event) {
            console.log(tab.name)
            if(tab.name === 'second'){
                let newWeek = this.currWeek;
                let year = newWeek.getFullYear().toString();
                let month = newWeek.getMonth().toString();
                let day = newWeek.getDate().toString();
                let yearweek = year + this.getWeekStr(year,month,day)
                this.getWeeklys(yearweek)
            }else{
                let newWeek = new Date();
                let year = newWeek.getFullYear().toString();
                let month = newWeek.getMonth().toString();
                let day = newWeek.getDate().toString();
                let yearweek = year + this.getWeekStr(year,month,day)
                this.getCurrWeekReport(yearweek)
            }
        },
        getWeekStr:function (year,month,day) {
            let date1 = new Date(year,month,day);
            let date2 = new Date(year, '0', '1');
            let d = Math.round((date1.valueOf() - date2.valueOf()) / 86400000);
            return Math.ceil((d + ((date2.getDay() + 1) - 1)) / 7);
        },
        importLastWeekReport:function(){
            let _this = this;
            axios({
                method: 'get',
                url: 'weeklys/lastWeekReport',
            }).then(function (result) {
                if (result.data.success){
                    if(result.data.data !== null){
                        _this.weekly.thisWeekReport = result.data.data.nextWeekReport;
                    }
                }else {
                    _this.$message({
                        message:result.data.msg,
                        type:'error'
                    });
                }
            })
        },
        getCurrWeekReport:function(){
            let _this = this;
            axios({
                method: 'get',
                url: 'weeklys/currWeekReport',
            }).then(function (result) {
                if (result.data.success){
                    if(result.data.data !== null){
                        _this.weekly = result.data.data;
                    }
                }else {
                    _this.$message({
                        message:result.data.msg,
                        type:'error'
                    });
                }
            })
        },
        importLastWeekGitLogs:function(){
            let _this = this;
            axios({
                method: 'get',
                url: 'gitAnalysiss/lastWeekCommitLogs',
            }).then(function (result) {
                if (result.data.success){
                    if(result.data.data !== null){
                        _this.weekly.thisWeekReport += result.data.data;
                    }
                }else {
                    _this.$message({
                        message:result.data.msg,
                        type:'error'
                    });
                }
            })
        },
        getWeeklys:function(week){
            let _this = this;
            axios({
                method: 'get',
                url: 'weeklys/'+week+'/all',
            }).then(function (result) {
                if (result.data.success){
                    if(result.data.data !== null){
                        _this.weeklys = result.data.data;
                    }
                }else {
                    _this.$message({
                        message:result.data.msg,
                        type:'error'
                    });
                }
            })
        },
        submitForm(formName) {
            let _this = this;
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    axios({
                        method: 'post',
                        url: 'weeklys',
                        data: _this.weekly
                    }).then(function (result) {
                        if (result.data.success){
                            this.typeSelect = '';
                            Bus.$emit("optionsCode",this.typeSelect);
                        }else {
                            _this.$message({
                                message:result.data.msg,
                                type:'error'
                            });
                        }
                    })
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });
        }
    },

})