var project_date = Vue.component('project-date',{
    template:`
        <el-container>
           <el-header style="height: 140px">
            <div style="background-color: white;height: 160px;margin-top: 10px">
                <div style="height: 90px;width: 100%;margin-top: 30px">
                    <h3>项目排期</h3>
                    <span>每个有意义的项目都应该有个特别的代号。
                        <a href="#" style="color: #ef9b6c" @click="createProject('pro')">创建项目排期  </a>
                        <a href="#" style="color: #ef9b6c" @click="createProject('dem')">创建日常需求  </a>
                       </span>
                </div>
            </div>
        </el-header>
        <el-main class="index-el-main">
            <div style="margin-bottom: 20px" v-for="template in templates">
                <el-card class="box-card">
                    <div slot="header" class="clearfix">
                        <span v-text="template.projectName"></span>
                        <el-button style="float: right; padding: 3px 0" type="text" @click="projectDelay(template.id)">项目延期</el-button>
                        <el-button style="float: right; padding: 3px 0" type="text" @click="editTemplate(template.id)">编辑</el-button>
                        <el-button style="float: right; padding: 3px 0" type="text" @click="sendMail(template.id)">发送到邮件</el-button>
                        <el-tooltip  placement="top" effect="light">
                             <div slot="content">
                                <div style="height: 20px">产品经理: {{template.projectManger}}</div>
                                <div style="height: 20px">项目经理: {{template.owner}}</div>
                                <div style="height: 20px">本组开发: {{template.groupMembers}}</div>
                                <div style="height: 20px">后端开发: {{template.projectServer}}</div>
                                <div style="height: 20px">前端开发: {{template.projectFront}}</div>
                                <div style="height: 20px">测试人员: {{template.projectTester}}</div>
                            </div>
                            <el-button style="float: right; padding: 3px 0" type="text" icon="el-icon-warning">全部参与人员</el-button>
                        </el-tooltip>
                    </div>
                    <div style="" style="padding-left: 0;padding-right: 0">
                        <el-steps :active="actives[0]" align-center finish-status="success">
                        <el-step title="接口评审" :description="template.interfaceReview  | dataFormat('yyyy-MM-dd')"></el-step>
                        <el-step title="需求评审" :description="template.caseReview | dataFormat('yyyy-MM-dd')"></el-step>
                        <el-step title="接口提测" :description="template.interfaceTest | dataFormat('yyyy-MM-dd')"></el-step>
                        <el-step title="测试" :description="template.allTest  | dataFormat('yyyy-MM-dd')"></el-step>
                        <el-step title="预发" :description="template.preDate  | dataFormat('yyyy-MM-dd')"></el-step>
                        <el-step title="发布" :description="template.produceDate  | dataFormat('yyyy-MM-dd')"></el-step>
                    </el-steps>
                    </div>
                    <hr style="border: none; height: 1px; color: #ebeef5; background-color: #ebeef5;">
                    <div style="text-align: center">
                        <a href="#" @click="details(template.id)">查看项目详情</a>
                    </div>
                </el-card>
            </div>
            
        </el-main>

    </el-container>
    `,
    data(){
        return{
            actives: [3],
            templates:[]
        }
    },
    props:['optionsCode'],
    mounted:function(){
        let _this = this;
        axios({
            method: 'get',
            url: 'projectTemplates',
        }).then(function (result) {
            if (result.data.success){
                _this.templates = result.data.data;
            }else {
                _this.$message({
                    message:result.data.msg,
                    type:'error'
                });
            }
        })
    },
    methods:{
        createProject:function(type){
            if (type == 'pro'){
                this.typeSelect = 'createProject'
            }else if(type == 'dem'){
                this.typeSelect = 'createDemand'
            }
            Bus.$emit("optionsCode",this.typeSelect);
        },
        editTemplate:function(templateId){
            Bus.$emit("templateId",templateId);
            Bus.$emit("optionsCode","editProject");
        },
        sendMail:function(templateId){
            let _this = this;
            axios({
                method: 'get',
                url: 'projectTemplates/'+templateId+"/sendMail",
            }).then(function (result) {
                _this.$message({
                    message:result.data.msg,
                    type:'success'
                });
            })
        },
        details:function(templateId){
            Bus.$emit("templateId",templateId);
            Bus.$emit("optionsCode","projectDelayInfo");
        },
        projectDelay:function(templateId){
            Bus.$emit("templateId",templateId);
            Bus.$emit("optionsCode","projectDelay");
        },
        next:function(item) {
            if (this.actives[item]++ > 4) {
                Vue.set(this.actives, item, 0)
            }else{
                Vue.set(this.actives, item, this.actives[item])
            };
        }
    },

})