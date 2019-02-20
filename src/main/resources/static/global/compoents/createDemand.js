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
                   <el-form-item label="开发人员" prop="projectEnd">
                       <el-select
                            v-model="ruleForm.projectEnd"
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
                   
               
                   <el-form-item label="整体提测" prop="allTest">
                        <el-date-picker type="date" placeholder="选择日期" v-model="ruleForm.allTest" style="width: 100%;"></el-date-picker>
                   </el-form-item>
                   <el-form-item label="预发环境" prop="preDate">
                        <el-date-picker type="date" placeholder="选择日期" v-model="ruleForm.preDate" style="width: 100%;"></el-date-picker>
                   </el-form-item>
                   <el-form-item label="生产环境" prop="produceDate">
                        <el-date-picker type="date" placeholder="选择日期" v-model="ruleForm.produceDate" style="width: 100%;"></el-date-picker>
                   </el-form-item>
                   <el-form-item label="备注" prop="remark">
                       <el-input type='tel' v-model="ruleForm.remark"></el-input>
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
                projectName: '',
                projectManger: '',
                projectPrd:'',
                projectEnd: [],
                allTest:'',
                preDate:'',
                produceDate:'',
                remark:''
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
                projectName: [
                    { required: true, message: '请输入应用中文名称', trigger: 'blur' },
                ],
                projectManger: [
                    { required: true, message: '请填写产品经理', trigger: 'blur' }
                ],
                projectPrd: [
                    {  required: true, message: '请填写需求文档地址', trigger: 'blur' }
                ],
                projectEnd: [
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
    methods:{
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