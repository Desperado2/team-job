var delay_project = Vue.component('delay-project',{
    template:`
        <el-container>
        <el-header style="height: 40px">
           
        </el-header>
            <el-main class="index-el-main">
           <el-card class="box-card" style="padding: 10px 20%">
                   <div slot="header" class="clearfix">
                        <span v-text="ruleForm.projectName"></span>
                    </div>
               <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="150px" class="demo-ruleForm" >
                  
                   <el-form-item label="接口评审" prop="interfaceReview">
                        <el-date-picker type="date" placeholder="选择日期" v-model="ruleForm.interfaceReview" style="width: 100%;"></el-date-picker>
                   </el-form-item>
                   <el-form-item label="用例评审" prop="caseReview">
                        <el-date-picker type="date" placeholder="选择日期" v-model="ruleForm.caseReview" style="width: 100%;"></el-date-picker>
                   </el-form-item>
                   <el-form-item label="接口提测" prop="interfaceTest">
                        <el-date-picker type="date" placeholder="选择日期" v-model="ruleForm.interfaceTest" style="width: 100%;"></el-date-picker>
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
                   <el-form-item label="延期原因" prop="remark">
                       <el-input type="textarea" rows="4"  v-model="ruleForm.remark"></el-input>
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
            imageUrl:'',
            ruleForm: {
                projectName: '',
                projectManger: '',
                projectLevel: '',
                projectProperty : '',
                projectPrd:'',
                projectEnd: [],
                projectFront:'',
                projectTester:'',
                interfaceReview:'',
                caseReview:'',
                interfaceTest:'',
                allTest:'',
                preDate:'',
                produceDate:'',
                remark:''
            },
            users:[],
            rules: {
                interfaceReview: [
                    {  required: true, message: '请选择日期', trigger: 'blur' }
                ],
                caseReview: [
                    {  required: true, message: '请选择日期', trigger: 'blur' }
                ],
                interfaceTest: [
                    {  required: true, message: '请选择日期', trigger: 'blur' }
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
                remark: [
                    {  required: true, message: '请填写延期原因', trigger: 'blur' }
                ],
            },
            templateId : this.templateId
        }
    },
    props:['optionsCode','templateId'],
    mounted:function(){
        let _this = this;
        axios({
            method: 'get',
            url: 'projectTemplates/'+_this.templateId,
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
    methods:{
        submitForm(formName) {
            let _this = this;
            this.$refs[formName].validate((valid) => {
                if (valid) {
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