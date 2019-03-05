var edit_project = Vue.component('edit-project',{
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
                       <el-select v-model="ruleForm.projectProperty" placeholder="请选择项目属性">
                          <el-option label="新建项目" value="新建项目"></el-option>
                          <el-option label="版本迭代" value="版本迭代"></el-option>
                          <el-option label="技术优化" value="技术优化"></el-option>
                       </el-select>
                   </el-form-item>
                    <el-form-item label="需求文档地址" prop="projectPrd">
                       <el-input v-model="ruleForm.projectPrd"></el-input>
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
                   <el-form-item label="服务端开发" prop="projectServer">
                       <el-input type='tel' v-model="ruleForm.projectServer"></el-input>
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
                   <el-form-item label="备注" prop="content">
                       <el-input type="textarea" rows="4" v-model="ruleForm.content"></el-input>
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
                groupMembers: [
                    { required: true, message: '请选择本团队开发人员', trigger: 'change' },
                ],
                projectServer: [
                    {  required: true, message: '请填写服务端开发人员', trigger: 'blur' }
                ],projectFront: [
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
            },
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
                let coders =_this.formatCoder(_this.ruleForm.groupMembers);
                _this.ruleForm.groupMembers = coders;
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
        formatCoder:function(coders) {
            let coderss = [];
            for (let coder of coders.split(',')) {
                coderss.push(coder)
            }
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