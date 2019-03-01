var create_app = Vue.component('create-app',{
    template:`
        <el-container>
        <el-header style="height: 40px">
           
        </el-header>
            <el-main class="index-el-main">
           <el-card class="box-card" style="padding: 10px 20%">
               <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="150px" class="demo-ruleForm" >
                  
                   <el-form-item label="项目名" prop="projectName">
                       <el-input v-model="ruleForm.projectName"></el-input>
                   </el-form-item>
                   <el-form-item label="中文名" prop="projectRealName">
                       <el-input v-model="ruleForm.projectRealName"></el-input>
                   </el-form-item>
                   
                   <el-form-item label="仓库地址" prop="repositoryUrl">
                       <el-input v-model="ruleForm.repositoryUrl"></el-input>
                   </el-form-item>
                   <el-form-item label="仓库类型" prop="repositoryType">
                       <el-select v-model="ruleForm.repositoryType" placeholder="请选择仓库类型">
                                    <el-option
                                      v-for="item in responTypes"
                                      :key="item.value"
                                      :label="item.name"
                                      :value="item.value"
                                      >
                                    </el-option>
                   </el-form-item>
                   <el-form-item label="文档地址" prop="documentUrl">
                       <el-input  v-model="ruleForm.documentUrl"></el-input>
                   </el-form-item>
                   <el-form-item label="开发环境数据库" prop="databaseUrl">
                       <el-input type='tel' v-model="ruleForm.databaseUrl"></el-input>
                   </el-form-item>
                    <el-form-item label="项目创建日期" prop="projectDateCreate">
                       <el-date-picker type="date" placeholder="选择日期" v-model="ruleForm.projectDateCreate" style="width: 100%;"></el-date-picker>
                   </el-form-item>
                   <el-form-item label="开发人员" prop="coders">
                       <el-select
                            v-model="ruleForm.coders"
                            multiple
                            filterable
                            allow-create
                            default-first-option
                            placeholder="请选择开发人员">
                            <el-option
                              v-for="user in users"
                              :key="user.id"
                              :label="user.name"
                              :value="user.id">
                              <span style="float: left"><img :src="user.headUrl" style="width: 20px;border-radius: 50%"></span>
                               <span >{{ user.name }}</span>
                            </el-option>
                          </el-select>
                   </el-form-item>
                   <el-form-item>
                       <el-button type="primary" @click="submitForm('ruleForm')">立即创建</el-button>
                       <el-button @click="back">返回</el-button>
                   </el-form-item>
               </el-form>
           </el-card>

       </el-main>
        </el-container>
    `,
    data(){
        return{
            typeSelect:this.optionsCode,
            imageUrl:'',
            ruleForm: {
                projectRealName:'',
                projectName: '',
                repositoryUrl: '',
                documentUrl: '',
                databaseUrl : '',
                projectDateCreate:'',
                coder: [],
            },
            responTypes:[
                {name:'Git',value:1},
                {name:'Svn',value:0}
            ],
            users:[],
            rules: {
                projectDateCreate:[
                    { type: 'date', required: true, message: '请选择日期', trigger: 'change' }
                ],
                projectRealName: [
                    { required: true, message: '请输入应用中文名称', trigger: 'blur' },
                ],
                repositoryType: [
                    { required: true, message: '请选择仓库类型', trigger: 'change' },
                ],
                projectName: [
                    { required: true, message: '请输入应用名称', trigger: 'blur' },
                ],
                repositoryUrl: [
                    { required: true, message: '请填写ssh地址', trigger: 'blur' }
                ],
                documentUrl: [
                    { required: true, message: '请填写文档地址', trigger: 'blur' }
                ],
                databaseUrl: [
                    {  required: true, message: '请填写数据库', trigger: 'blur' }
                ],
                coder: [
                    { required: true, message: '请填写开发人员', trigger: 'change' },
                ]
            }
        }
    },
    mounted:function(){
        let _this = this;
        axios({
            method: 'get',
            url: 'users/noSplit',
        }).then(function (result) {
            if (result.data.success){
                _this.users = result.data.data;
            }else {
                _this.$message({
                    message:result.data.msg,
                    type:'error'
                });
            }
        })
    },
    props:['optionsCode'],
    methods:{
        lookUserInfo(name){
            this.typeSelect = 'addUser'
            Bus.$emit("optionsCode",this.typeSelect);
        },
        editCoder:function(coders){
            let coderss = ''
            for (let coder of coders){
                coderss += coder+","
            }
            coderss = coderss.substring(0,coderss.length-1)
            return coderss
        },
        submitForm(formName) {
            let _this = this
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    _this.ruleForm.coder = _this.editCoder(_this.ruleForm.coder)
                    axios({
                        method: 'post',
                        url: 'projects',
                        data: _this.ruleForm
                    }).then(function (result) {
                        if (result.data.success){
                            this.typeSelect = ''
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
        },
        back(){
            this.typeSelect = ''
            Bus.$emit("optionsCode",this.typeSelect);
        },
    },

})