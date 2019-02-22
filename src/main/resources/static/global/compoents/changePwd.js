var change_pwd = Vue.component('change-pwd',{
    template:`
        <el-container>
        <el-header style="height: 40px">
           
        </el-header>
            <el-main class="index-el-main">
           <el-card class="box-card" style="padding: 10px 30%">
               <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm" >
                  
                  <el-form-item label="旧密码" prop="oldPassword">
                       <el-input type="password" v-model="ruleForm.oldPassword"></el-input>
                   </el-form-item>
                   <el-form-item label="新密码" prop="newPassword">
                       <el-input type="password" v-model="ruleForm.newPassword"></el-input>
                   </el-form-item>
                   <el-form-item>
                       <el-button type="primary" @click="submitForm('ruleForm')">更新</el-button>
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
            ruleForm: {
                newPassword: '',
                oldPassword : '',
            },
            rules: {
                newPassword: [
                    { required: true, message: '请填写旧密码', trigger: 'blur' },
                    { min: 6, max: 16, message: '长度在 6 到 16 个字符', trigger: 'blur' }
                ],
                oldPassword: [
                    { required: true, message: '请填写新密码', trigger: 'blur' },
                    { min: 6, max: 16, message: '长度在 6 到 16 个字符', trigger: 'blur' }
                ]
            }
        }
    },
    props:['optionsCode'],
    methods:{
        back(){
            this.typeSelect = 'personalCenter'
            Bus.$emit("optionsCode",this.typeSelect);
        },
        submitForm(formName) {
            let _this = this;
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    axios({
                        method: 'post',
                        url: 'users/editPwd',
                        data: _this.ruleForm
                    }).then(function (result) {
                        if (result.data.success){
                            this.typeSelect = 'personalCenter'
                            Bus.$emit("optionsCode",this.typeSelect);
                        }else {
                            _this.$message({
                                message:result.data.msg,
                                type:'error'
                            });
                        }
                    })
                } else {
                    return false;
                }
            });
        }
    },

})