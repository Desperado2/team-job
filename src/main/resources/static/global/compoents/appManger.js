var app_manager = Vue.component('app-manager',{
    template:`
        <el-container>
          <el-header style="height: 120px">
            <div style="background-color: white;height: 140px;margin-top: 10px">
            <div style="height: 90px;width: 100%;margin-top: 30px">
                    <h3>应用列表</h3>
                    <span>每个有意义的项目都应该有个特别的代号。
                    <a href="#" @click="createApp" style="color: #ef9b6c">创建新应用</a> </span>
            </div>
            </div>
        </el-header>
        <el-main class="index-el-main">
            <el-card class="box-card">
                <div slot="header" class="clearfix">
                    <el-radio-group v-model="selectItem" :change="selectedItem(selectItem)">
                      <el-radio-button label="只看自己"></el-radio-button>
                      <el-radio-button label="全部"></el-radio-button>
                    </el-radio-group>
                 
                    <el-button style="float: right; padding: 3px 0" type="text">维护{{allApps.length | numberToChinese}}个项目</el-button>
                </div>

                <el-table
                        :data="allApps"
                        style="width: 100%">
                    <el-table-column
                            prop="projectRealName"
                            label="应用"
                            width="180">
                    </el-table-column>
                    <el-table-column
                            prop="documentUrl"
                            label="swagger地址"
                     >
                    </el-table-column>
                    <el-table-column
                            prop="repositoryUrl"
                            label="仓库HTTP地址">
                    </el-table-column>
                    <el-table-column
                            prop="repositoryTypeName"
                            label="仓库类型"
                            width="100">
                    </el-table-column>
                    <el-table-column
                            prop="coders"
                            label="拥有人">
                        <template slot-scope="scope" >
                            <img v-for="user in scope.row.coderList" :src="user.headUrl" style="width: 20px;border-radius: 50%">
                        </template>
                    </el-table-column>
                    <el-table-column
                            width="180"
                            label="操作">
                        <template slot-scope="scope">
                            <el-button @click="lookAPP(scope.row.id)" type="text" size="small">查看</el-button>
                            <el-button type="text" size="small" @click="editAPP(scope.row.id)">编辑</el-button>
                            <el-button type="text" size="small" @click="deleteAPP(scope.row.id)">删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </el-card>
        </el-main>
        </el-container>
    `,
    data(){
        return{
            selectItem: '只看自己',
            allApps:[],
            app_onlyme: [],
            app_all:[],
            typeSelect:this.optionsCode,
        }
    },
    mounted:function(){
        if(this.selectItem === '只看自己'){
            this.findDataByUserId();
        }else {
            this.findData();
        }
    },
    props:['optionsCode','projectId'],
    methods:{
        selectedItem:function(item){
            if(item === '只看自己'){
                if(this.app_onlyme.length === 0){
                    this.findDataByUserId();
                }
                this.allApps = this.app_onlyme
            }else{
                if(this.app_all.length === 0){
                    this.findData()
                }
                this.allApps = this.app_all
            }
        },
        lookAPP:function(projectId){
            Bus.$emit("projectId",projectId);
            Bus.$emit("optionsCode","appDetail");
        },
        editAPP:function(projectId){
            Bus.$emit("projectId",projectId);
            Bus.$emit("optionsCode","editApp");
        },
        deleteAPP:function(projectId){
            let _this = this;
            this.$confirm('此操作将删除该应用, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                axios({
                    method: 'delete',
                    url: 'projects/'+projectId,
                }).then(function (result) {
                    if (result.data.success) {
                        _this.$message({
                            type: 'success',
                            message: '删除成功!'
                        });
                    } else {
                        _this.$message({
                            message: result.data.msg,
                            type: 'error'
                        });
                    }
                })
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });
        },
        createApp:function () {
            this.typeSelect = 'createApp';
            Bus.$emit("optionsCode",this.typeSelect);
        },
        findData:function () {
            let _this = this;
            axios({
                method: 'get',
                url: 'projects',
            }).then(function (result) {
                if (result.data.success){
                    _this.app_all = result.data.data;
                    this.allApps = this.app_all
                }else {
                    _this.$message({
                        message:result.data.msg,
                        type:'error'
                    });
                }
            })
        },
        findDataByUserId:function () {
            let _this = this;
            axios({
                method: 'get',
                url: 'projects/user',
            }).then(function (result) {
                if (result.data.success) {
                    _this.app_onlyme = result.data.data;
                    _this.allApps =  result.data.data;
                } else {
                    _this.$message({
                        message: result.data.msg,
                        type: 'error'
                    });
                }
            })
        }
    },

})