var create_demand= Vue.component('create-demand',{
    template:`
        <el-container>
        <el-header style="height: 40px">
           
        </el-header>
            <el-main class="index-el-main">
           <el-card class="box-card" style="padding: 10px 20%">
               <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="150px" class="demo-ruleForm" >
                  
                   <el-form-item label="需求名称" prop="projectName">
                       <el-input v-model="ruleForm.projectName"></el-input>
                   </el-form-item>
                   <el-form-item label="产品经理" prop="projectManger">
                       <el-input v-model="ruleForm.projectManger"></el-input>
                   </el-form-item>
             
                    <el-form-item label="需求文地址" prop="projectPrd">
                       <el-input type='tel' v-model="ruleForm.projectPrd"></el-input>
                   </el-form-item>
                   <el-form-item>
                        <el-select
                            v-model="ruleForm.groupMembers"
                            multiple
                            filterable
                            allow-create
                            default-first-option
                            placeholder="请选择本团队开发人员">
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
               
                   <el-form-item label="整体提测" prop="allTest">
                        <el-date-picker type="date" placeholder="选择日期" v-model="ruleForm.allTest" style="width: 100%;"></el-date-picker>
                   </el-form-item>
                   <el-form-item label="预发环境" prop="preDate">
                        <el-date-picker type="date" placeholder="选择日期" v-model="ruleForm.preDate" style="width: 100%;"></el-date-picker>
                   </el-form-item>
                   <el-form-item label="生产环境" prop="produceDate">
                        <el-date-picker type="date" placeholder="选择日期" v-model="ruleForm.produceDate" style="width: 100%;"></el-date-picker>
                   </el-form-item>
                   <el-form-item label="备注" prop="content">
                       <el-input type='tel' v-model="ruleForm.content"></el-input>
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
            ruleForm: {
                projectName: '',
                projectManger: '',
                projectPrd:'',
                groupMembers: [],
                allTest:'',
                preDate:'',
                produceDate:'',
                remark:''
            },
            users:[],
            rules: {
                projectName: [
                    { required: true, message: '请输入应用名称', trigger: 'blur' },
                ],
                projectManger: [
                    { required: true, message: '请填写产品经理', trigger: 'blur' }
                ],
                projectPrd: [
                    {  required: true, message: '请填写需求文档地址', trigger: 'blur' }
                ],
                groupMembers: [
                    { required: true, message: '请填写开发人员', trigger: 'change' },
                ],
                allTest: [
                    {  required: true, message: '请选择日期', trigger: 'blur' }
                ],
                preDate: [
                    {  required: true, message: '请选择日期', trigger: 'blur' }
                ],
                produceDate: [
                    {  required: true, message: '请选择日期', trigger: 'blur' }
                ],
            }
        }
    },
    props:['optionsCode'],
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
    methods:{
        editCoder:function(coders){
            let coderss = '';
            for (let coder of coders){
                coderss += coder+","
            }
            coderss = coderss.substring(0,coderss.length-1)
            return coderss
        },
        submitForm(formName) {
            let _this = this;
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    _this.ruleForm.groupMembers = _this.editCoder(_this.ruleForm.groupMembers);
                    axios({
                        method: 'post',
                        url: 'projectTemplates',
                        data: _this.ruleForm
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
        },
        back(){
            this.typeSelect = '';
            Bus.$emit("optionsCode",this.typeSelect);
        },
    },

})