var week_report = Vue.component('week-report',{
    template:`
        <el-container>
          <el-header style="height: 120px">
            <div style="background-color: white;height: 140px;margin-top: 10px">
                <el-breadcrumb separator="/">
                    <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
                    <el-breadcrumb-item>周报</el-breadcrumb-item>
                </el-breadcrumb>
                <el-tabs v-model="activeName" @tab-click="handleClick" style="height: 70px;width: 100%;margin-top: 60px">
                    <el-tab-pane label="写周报" name="first"></el-tab-pane>
                    <el-tab-pane label="团队周报" name="second"></el-tab-pane>
                </el-tabs>
            </div>
        </el-header>
        <el-main class="index-el-main">
            <el-card class="box-card" style="padding: 0px 22%">
                <el-form ref="form" :model="form" label-width="100px">
                    <el-form-item label="本周工作:">
                        <el-row :gutter="20" style="border: 1px solid darkgray;margin-left: 0;margin-right: 0">
                            <el-col :span="6">
                                <el-button type="text"><i class="el-icon-edit"></i>导入上周周报</el-button>
                            </el-col>
                            <el-col :span="6">
                                <el-button type="text"><i class="el-icon-edit"></i>导入Git记录</el-button>
                            </el-col>
                            <el-col :span="6">
                                <el-button type="text" disabled><i class="el-icon-edit"></i>导入会议记录</el-button>
                            </el-col>
                        </el-row>
                        <el-input type="textarea" rows="10" v-model="form.lastweek"></el-input>
                    </el-form-item>
                    <el-form-item label="下周工作:">
                        <el-input type="textarea" rows="6" v-model="form.nextweek"></el-input>
                    </el-form-item>
                    <el-form-item label="感想(选填):">
                        <el-input type="textarea" rows="4" v-model="form.feeling"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" >提交</el-button>
                    </el-form-item>
                </el-form>
            </el-card>
        </el-main>
        </el-container>
    `,
    data(){
        return{
            activeName: 'first',
            form:{
                lastweek:'',
                nextweek:'',
                feeling:''
            }
        }
    },
    methods:{
        handleClick:function (tab, event) {
            console.log(tab.name)
        }
    },

})