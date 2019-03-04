var delay_project_info = Vue.component('delay-project-info',{
    template:`
        <el-container>
        <el-header style="height: 40px">
           
        </el-header>
            <el-main class="index-el-main">
           <el-card class="box-card" style="padding: 10px">
                   <div slot="header" class="clearfix">
                        <span v-text="templates.name"></span>
                    </div>
                    <el-row>
                      <el-col :span="12">
                        <div class="grid-content bg-purple" style="text-align: left">
                              <div class="block">
                                  <el-timeline>
                                    <el-timeline-item
                                      v-for="(activity, index) in templates['dateLines']"
                                      :key="index"
                                      :color="activity.color"
                                      :timestamp="activity.timestamp">
                                      {{activity.content}}
                                    </el-timeline-item>
                                  </el-timeline>
                                </div>
                           </div>
                      </el-col>
                      <el-col :span="12">
                        <div class="grid-content bg-purple-light" style="text-align: left">
                           <div>延期原因:</div>
                           <div v-text="templates['remark']"></div>
                         </div>
                      </el-col>
                    </el-row>
           </el-card>

       </el-main>
      </el-container>
    `,
    data(){
        return{
            typeSelect:this.optionsCode,
            templates:[],
        }
    },
    props:['optionsCode','templateId'],
    mounted:function(){
        let _this = this;
        axios({
            method: 'get',
            url: 'projectTemplates/'+_this.templateId+"/details",
        }).then(function (result) {
            if (result.data.success){
                _this.templates = result.data.data;
            }else {
                _this.$message({
                    message:result.data.msg,
                    type:'error'
                });
            }
        })
    },
    methods:{
        back(){
            this.typeSelect = '';
            Bus.$emit("optionsCode",this.typeSelect);
        },
    },

})