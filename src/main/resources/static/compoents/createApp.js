var create_app = Vue.component('create-app',{
    template:`
        <el-container>
        <el-header style="height: 40px">
           
        </el-header>
            <el-main class="index-el-main">
           <el-card class="box-card" style="padding: 10px 20%">
               <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="150px" class="demo-ruleForm" >
                  
                   <el-form-item label="中文名" prop="appName">
                       <el-input v-model="ruleForm.appName"></el-input>
                   </el-form-item>
                   <el-form-item label="仓库地址" prop="gitlab">
                       <el-input v-model="ruleForm.gitlab"></el-input>
                   </el-form-item>
                   <el-form-item label="文档地址" prop="swagger">
                       <el-input type="password" v-model="ruleForm.swagger"></el-input>
                   </el-form-item>
                   <el-form-item label="开发环境数据库" prop="database">
                       <el-input type='tel' v-model="ruleForm.database"></el-input>
                   </el-form-item>
                      
                   <el-form-item label="开发人员" prop="owners">
                       <el-select
                            v-model="ruleForm.owners"
                            multiple
                            filterable
                            allow-create
                            default-first-option
                            placeholder="请选择开发人员">
                            <el-option
                              v-for="item in options5"
                              :key="item.value"
                              :label="item.label"
                              :value="item.value">
                            </el-option>
                          </el-select>
                   </el-form-item>
                   <el-form-item>
                       <el-button type="primary" @click="submitForm('ruleForm')">立即创建</el-button>
                       <el-button @click="resetForm('ruleForm')">重置</el-button>
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
                AppName: '',
                gitlab: '',
                swagger: '',
                database : '',
                owners: [],
            },
            options5: [{
                value: 'HTML',
                label: 'HTML'
            }, {
                value: 'CSS',
                label: 'CSS'
            }, {
                value: 'JavaScript',
                label: 'JavaScript'
            }],
            rules: {
                appName: [
                    { required: true, message: '请输入应用中文名称', trigger: 'blur' },
                ],
                gitlab: [
                    { required: true, message: '请填写ssh地址', trigger: 'blur' }
                ],
                swagger: [
                    { required: true, message: '请填写文档地址', trigger: 'blur' }
                ],
                database: [
                    {  required: true, message: '请填写数据库', trigger: 'blur' }
                ],
                owners: [
                    { required: true, message: '请填写开发人员', trigger: 'change' },
                ]
            }
        }
    },
    props:['optionsCode'],
    methods:{
        lookUserInfo(name){
            this.typeSelect = 'addUser'
            Bus.$emit("optionsCode",this.typeSelect);
        },
        submitForm(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    alert('submit!');
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });
        },
        resetForm(formName) {
            this.$refs[formName].resetFields();
        }
    },

})