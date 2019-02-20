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
                    <el-button-group>
                        <el-button autofocus="true" @click="selectItem('only_me')">只看自己</el-button>
                        <el-button @click="selectItem('all')">全部</el-button>
                    </el-button-group>

                    <el-button style="float: right; padding: 3px 0" type="text">维护两个项目</el-button>
                </div>

                <el-table
                        :data="tableData"
                        style="width: 100%">
                    <el-table-column
                            prop="app"
                            label="应用"
                            width="180">
                    </el-table-column>
                    <el-table-column
                            prop="swagger"
                            label="swagger地址"
                            width="180">
                    </el-table-column>
                    <el-table-column
                            prop="gitlab"
                            label="仓库HTTP地址">
                    </el-table-column>
                    <el-table-column
                            prop="owners"
                            label="拥有人">
                        <template slot-scope="scope" >
                            <img v-for="image in scope.row.owners" :src="image" style="width: 20px;border-radius: 50%">
                        </template>
                    </el-table-column>
                    <el-table-column
                            prop="options"
                            label="操作">
                        <template slot-scope="scope">
                            <el-button @click="handleClick(scope.row)" type="text" size="small">查看</el-button>
                            <el-button type="text" size="small">编辑</el-button>
                            <el-button type="text" size="small">删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </el-card>
        </el-main>
        </el-container>
    `,
    data(){
        return{
            selectedItem: 'only_me',
            tableData:[],
            tableData_onlyme: [{
                app: '2016-05-02',
                swagger: '王小虎',
                gitlab: '上海市普陀区金沙江路 1518 弄',
                owners:[
                    './global/image/default.jpg',
                    './global/image/tx/czm.jpg',
                    './global/image/tx/hb.jpg'
                ],

            }, {
                app: '2016-05-02',
                swagger: '王小虎',
                gitlab: '上海市普陀区金沙江路 1518 弄',
                owners:[
                    './global/image/default.jpg'
                ],
            }, {
                app: '2016-05-02',
                swagger: '王小虎',
                gitlab: '上海市普陀区金沙江路 1518 弄',
                owners:[
                    './global/image/default.jpg',
                    './global/image/tx/czm.jpg',
                    './global/image/tx/hb.jpg',
                    './global/image/default.jpg',
                    './global/image/tx/czm.jpg',
                    './global/image/tx/hb.jpg',
                ],
            }, {
                app: '2016-05-02',
                swagger: '王小虎',
                gitlab: '上海市普陀区金沙江路 1518 弄',
                owners:[
                    './global/image/default.jpg',
                    './global/image/tx/czm.jpg',
                    './global/image/tx/hb.jpg'
                ],
            }],

            tableData_all: [{
                app: '2016-05-02',
                swagger: '王小虎',
                gitlab: '上海市普陀区金沙江路 1518 弄',
                owners:[
                    './global/image/default.jpg',
                    './global/image/tx/czm.jpg',
                    './global/image/tx/hb.jpg'
                ],

            }, {
                app: '2016-05-02',
                swagger: '王小虎',
                gitlab: '上海市普陀区金沙江路 1518 弄',
                owners:[
                    './global/image/default.jpg'
                ],
            }, {
                app: '2016-05-02',
                swagger: '王小虎',
                gitlab: '上海市普陀区金沙江路 1518 弄',
                owners:[
                    './global/image/default.jpg',
                    './global/image/tx/czm.jpg',
                    './global/image/tx/hb.jpg',
                    './global/image/default.jpg',
                    './global/image/tx/czm.jpg',
                    './global/image/tx/hb.jpg',
                ],
            }, {
                app: '2016-05-02',
                swagger: '王小虎',
                gitlab: '上海市普陀区金沙江路 1518 弄',
                owners:[
                    './global/image/default.jpg',
                    './global/image/tx/czm.jpg',
                    './global/image/tx/hb.jpg'
                ],
            },{
                app: '2016-05-02',
                swagger: '王小虎',
                gitlab: '上海市普陀区金沙江路 1518 弄',
                owners:[
                    './global/image/default.jpg',
                    './global/image/tx/czm.jpg',
                    './global/image/tx/hb.jpg'
                ],

            }, {
                app: '2016-05-02',
                swagger: '王小虎',
                gitlab: '上海市普陀区金沙江路 1518 弄',
                owners:[
                    './global/image/default.jpg'
                ],
            }, {
                app: '2016-05-02',
                swagger: '王小虎',
                gitlab: '上海市普陀区金沙江路 1518 弄',
                owners:[
                    './global/image/default.jpg',
                    './global/image/tx/czm.jpg',
                    './global/image/tx/hb.jpg',
                    './global/image/default.jpg',
                    './global/image/tx/czm.jpg',
                    './global/image/tx/hb.jpg',
                ],
            }, {
                app: '2016-05-02',
                swagger: '王小虎',
                gitlab: '上海市普陀区金沙江路 1518 弄',
                owners:[
                    './global/image/default.jpg',
                    './global/image/tx/czm.jpg',
                    './global/image/tx/hb.jpg'
                ],
            },{
                app: '2016-05-02',
                swagger: '王小虎',
                gitlab: '上海市普陀区金沙江路 1518 弄',
                owners:[
                    './global/image/default.jpg',
                    './global/image/tx/czm.jpg',
                    './global/image/tx/hb.jpg'
                ],

            }, {
                app: '2016-05-02',
                swagger: '王小虎',
                gitlab: '上海市普陀区金沙江路 1518 弄',
                owners:[
                    './global/image/default.jpg'
                ],
            }, {
                app: '2016-05-02',
                swagger: '王小虎',
                gitlab: '上海市普陀区金沙江路 1518 弄',
                owners:[
                    './global/image/default.jpg',
                    './global/image/tx/czm.jpg',
                    './global/image/tx/hb.jpg',
                    './global/image/default.jpg',
                    './global/image/tx/czm.jpg',
                    './global/image/tx/hb.jpg',
                ],
            }, {
                app: '2016-05-02',
                swagger: '王小虎',
                gitlab: '上海市普陀区金沙江路 1518 弄',
                owners:[
                    './global/image/default.jpg',
                    './global/image/tx/czm.jpg',
                    './global/image/tx/hb.jpg'
                ],
            },{
                app: '2016-05-02',
                swagger: '王小虎',
                gitlab: '上海市普陀区金沙江路 1518 弄',
                owners:[
                    './global/image/default.jpg',
                    './global/image/tx/czm.jpg',
                    './global/image/tx/hb.jpg'
                ],

            }, {
                app: '2016-05-02',
                swagger: '王小虎',
                gitlab: '上海市普陀区金沙江路 1518 弄',
                owners:[
                    './global/image/default.jpg'
                ],
            }, {
                app: '2016-05-02',
                swagger: '王小虎',
                gitlab: '上海市普陀区金沙江路 1518 弄',
                owners:[
                    './global/image/default.jpg',
                    './global/image/tx/czm.jpg',
                    './global/image/tx/hb.jpg',
                    './global/image/default.jpg',
                    './global/image/tx/czm.jpg',
                    './global/image/tx/hb.jpg',
                ],
            }, {
                app: '2016-05-02',
                swagger: '王小虎',
                gitlab: '上海市普陀区金沙江路 1518 弄',
                owners:[
                    './global/image/default.jpg',
                    './global/image/tx/czm.jpg',
                    './global/image/tx/hb.jpg'
                ],
            }],
            typeSelect:this.optionsCode,
        }
    },
    props:['optionsCode'],
    mounted:function(){
        this.tableData = this.tableData_onlyme
    },
    methods:{
        selectItem:function(item){
            this.selectedItem = item
            if(item=='only_me'){
                this.tableData = this.tableData_onlyme
            }else{
                this.tableData = this.tableData_all
            }
        },
        createApp:function () {
            this.typeSelect = 'createApp'
            Bus.$emit("optionsCode",this.typeSelect);
        }
    },

})