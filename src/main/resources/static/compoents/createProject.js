var create_project = Vue.component('create-project',{
    template:`
        <el-container>
        <el-header style="height: 40px">
           
        </el-header>
            <el-main class="index-el-main">
           <el-card class="box-card" style="padding: 10px 20%">
               <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="150px" class="demo-ruleForm" >
                  
                   <el-form-item label="项目名称" prop="projectName">
                       <el-input v-model="ruleForm.projectName"></el-input>
                   </el-form-item>
                   <el-form-item label="产品经理" prop="projectManger">
                       <el-input v-model="ruleForm.projectManger"></el-input>
                   </el-form-item>
                   <el-form-item label="项目优先级" prop="projectLevel">
                       <el-select v-model="ruleForm.projectLevel" placeholder="请选择项目优先级">
                          <el-option label="p0" value="p0"></el-option>
                          <el-option label="p1" value="p1"></el-option>
                          <el-option label="p2" value="p2"></el-option>
                          <el-option label="p3" value="p3"></el-option>
                       </el-select>
                   </el-form-item>
                   <el-form-item label="项目属性" prop="projectProperty">
                       <el-select v-model="ruleForm.projectLevel" placeholder="请选择项目属性">
                          <el-option label="新建项目" value="新建项目"></el-option>
                          <el-option label="版本迭代" value="版本迭代"></el-option>
                          <el-option label="技术优化" value="技术优化"></el-option>
                       </el-select>
                   </el-form-item>
                    <el-form-item label="需求文地址" prop="projectPrd">
                       <el-input type='tel' v-model="ruleForm.projectPrd"></el-input>
                   </el-form-item>
                   <el-form-item label="后端人员" prop="projectEnd">
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
                    <el-form-item label="客户端/前端" prop="projectFront">
                       <el-input type='tel' v-model="ruleForm.projectFront"></el-input>
                   </el-form-item>
                   <el-form-item label="测试人员" prop="projectTester">
                       <el-input type='tel' v-model="ruleForm.projectTester"></el-input>
                   </el-form-item>
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
                projectLevel: [
                    { required: true, message: '请选择项目优先级', trigger: 'change' }
                ],
                projectProperty: [
                    {  required: true, message: '请选择项目属性', trigger: 'change' }
                ],
                projectPrd: [
                    {  required: true, message: '请填写需求文档地址', trigger: 'blur' }
                ],
                projectEnd: [
                    { required: true, message: '请填写后端开发人员', trigger: 'change' },
                ],
                projectFront: [
                    {  required: true, message: '请填写前端开发人员', trigger: 'blur' }
                ],
                projectTester: [
                    {  required: true, message: '请填写测试人员', trigger: 'blur' }
                ],
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