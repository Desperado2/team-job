var add_user = Vue.component('add-user',{
    template:`
        <el-container>
        <el-header style="height: 40px">
           
        </el-header>
            <el-main class="index-el-main">
           <el-card class="box-card" style="padding: 10px 30%">
               <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm" >
                   <el-form-item label="头像" prop="headUrl">
                       <el-upload
                               class="avatar-uploader"
                               action="users/pic"
                               :show-file-list="false"
                               :on-success="handleAvatarSuccess"
                               :before-upload="beforeAvatarUpload">
                           <img v-if="imageUrl" :src="imageUrl" class="avatar">
                           <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                       </el-upload>
                   </el-form-item>
                   <el-form-item label="姓名" prop="name">
                       <el-input v-model="ruleForm.name"></el-input>
                   </el-form-item>
                   <el-form-item label="邮箱" prop="email">
                       <el-input v-model="ruleForm.email"></el-input>
                   </el-form-item>
                   <el-form-item label="初始密码" prop="password">
                       <el-input type="password" v-model="ruleForm.password"></el-input>
                   </el-form-item>
                   <el-form-item label="手机号" prop="phone">
                       <el-input type='tel' v-model="ruleForm.phone"></el-input>
                   </el-form-item>
                   <el-form-item label="生日" prop="birthday">
                       <div>
                           <el-radio-group v-model="ruleForm.birthType" size="small">
                               <el-radio-button label="农历"></el-radio-button>
                               <el-radio-button label="阳历" ></el-radio-button>
                           </el-radio-group>
                       </div>
                       <el-date-picker type="date" placeholder="选择日期" v-model="ruleForm.birthday" style="width: 100%;"></el-date-picker>
                   </el-form-item>
                   <el-form-item label="Git/SVN账号" prop="repositoryUsername">
                       <el-input v-model="ruleForm.repositoryUsername"></el-input>
                   </el-form-item>
                   <el-form-item label="部门" prop="department">
                       <el-input v-model="ruleForm.department"></el-input>
                   </el-form-item>
                   <el-form-item label="职务" prop="position">
                       <el-input v-model="ruleForm.position"></el-input>
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
                name: '',
                email: '',
                password: '',
                phone : '',
                birthday: '',
                birthType: '',
                department: '',
                position: '',
                repositoryUsername:'',
                headUrl:''
            },
            rules: {
                name: [
                    { required: true, message: '请输入姓名', trigger: 'blur' },
                    { min: 2, max: 5, message: '长度在 2 到 5 个字符', trigger: 'blur' }
                ],
                email: [
                    { type:'email',required: true, message: '请填写邮箱', trigger: 'blur' }
                ],
                phone: [
                    { required: true, message: '请填写手机', trigger: 'blur' }
                ],
                birthday: [
                    { type: 'date', required: true, message: '请选择日期', trigger: 'change' }
                ],
                password: [
                    { required: true, message: '请填写密码', trigger: 'blur' },
                    { min: 6, max: 16, message: '长度在 6 到 16 个字符', trigger: 'blur' }
                ],
                birth_type: [
                    {  required: true, message: '请选择生日类型', trigger: 'change' }
                ],
                repositoryUsername: [
                    { required: true, message: 'Git/SVN账号', trigger: 'blur' }
                ],
            }
        }
    },
    props:['optionsCode'],
    methods:{
        lookUserInfo(name){
            this.typeSelect = 'addUser'
            Bus.$emit("optionsCode",this.typeSelect);
        },
        handleAvatarSuccess(response, file, fileList) {
            this.imageUrl = URL.createObjectURL(file.raw);
            this.ruleForm.headUrl = response
            if(response == 'fail'){
                this.$message({
                    message:'图片上传失败，请重试',
                    type:'error'
                });
            }
        },
        beforeAvatarUpload(file) {
            const isJPG = file.type === 'image/jpeg' || 'image/png';
            const isLt2M = file.size / 1024 / 1024 < 2;

            if (!isJPG) {
                this.$message.error('上传头像图片只能是 JPG 格式!');
            }
            if (!isLt2M) {
                this.$message.error('上传头像图片大小不能超过 2MB!');
            }
            return isJPG && isLt2M;
        },

        submitForm(formName) {
            let _this = this;
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    axios({
                        method: 'post',
                        url: 'users',
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
                    return false;
                }
            });
        },
        resetForm(formName) {
            this.$refs[formName].resetFields();
        }
    },

})