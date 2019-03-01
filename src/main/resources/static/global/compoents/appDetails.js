var app_details = Vue.component('app-details',{
    template:`
        <el-container>
        <el-header style="height: 40px">
           
        </el-header>
            <el-main class="index-el-main">
           <el-card class="box-card" style="padding: 10px 10%">
               <el-form :model="ruleForm"  ref="ruleForm" label-width="150px" class="demo-ruleForm" disabled=true>
                  
                   <el-form-item label="中文名" prop="projectName">
                       <span v-text="ruleForm.projectName"></span>
                   </el-form-item>
                   <el-form-item label="仓库地址" prop="repositoryUrl">
                       <span v-text="ruleForm.repositoryUrl"></span>
                   </el-form-item>
                   <el-form-item label="仓库类型" prop="repositoryTypeName">
                       <span v-text="ruleForm.repositoryTypeName"></span>
                   </el-form-item>
                   <el-form-item label="文档地址" prop="documentUrl">
                       <span  v-text="ruleForm.documentUrl"></span>
                   </el-form-item>
                   <el-form-item label="开发环境数据库" prop="databaseUrl">
                       <span  v-text="ruleForm.databaseUrl"></span>
                   </el-form-item>
                   <el-form-item label="开发人员" prop="coderList">
                    <el-table
                        :data="ruleForm.coderList"
                        style="width: 100%">
                    <el-table-column
                            prop="headUrl"
                            label="头像"
                            width="40">
                        <template slot-scope="scope" >
                           <img :src="scope.row.headUrl" style="width: 20px;border-radius: 50%">
                        </template>
                    </el-table-column>
                    <el-table-column
                            prop="name"
                            label="姓名"
                     >
                    </el-table-column>
                    <el-table-column
                            prop="email"
                            label="email">
                    </el-table-column>
                    <el-table-column
                            prop="phone"
                            label="电话">
                    </el-table-column>
                    <el-table-column
                            prop="department"
                            label="部门">         
                    </el-table-column>
                </el-table>
                   </el-form-item>
                
               </el-form>
               
               <el-button type="primary" @click="back">返回</el-button>
           </el-card>

       </el-main>
        </el-container>
    `,
    data(){
        return{
            typeSelect:this.optionsCode,
            imageUrl:'',
            ruleForm: {
                projectName: '',
                repositoryUrl: '',
                repositoryTypeName:'',
                documentUrl: '',
                databaseUrl : '',
                coderList: [],
            },
            projectId:this.projectId,
        }
    },
    mounted:function(){
        let _this = this;
        axios({
            method: 'get',
            url: 'projects/'+ _this.projectId,
        }).then(function (result) {
            if (result.data.success){
                _this.ruleForm = result.data.data;
            }else {
                _this.$message({
                    message:result.data.msg,
                    type:'error'
                });
            }
        })
    },
    props:['optionsCode','projectId'],
    methods:{
        back(){
            this.typeSelect = '';
            Bus.$emit("optionsCode",this.typeSelect);
        },
    },

})