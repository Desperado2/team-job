var member_info = Vue.component('member-info',{
    template:`
        <el-container>
        <el-header style="height: 40px">
           
        </el-header>
        <el-main class="index-el-main">
            <el-card class="box-card" style="font-size: 14px">
                <el-row style="margin-bottom: 16px">
                    <el-col :span="24">
                        <img src="./global/image/tx/hj.png" width="100px" style="border-radius: 10px">
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12">
                        <span>
                            <i> <img src="./global/image/user.png" width="14px"></i>
                            昵称:
                        </span>
                        <span>张三</span>
                    </el-col>
                    <el-col :span="12">
                        <span>
                            <i> <img src="./global/image/mail.png" width="14px"></i>
                            邮箱:
                        </span>
                        <span>1928524965@qq.com</span>
                    </el-col>
                </el-row>
                <el-row style="margin-bottom: 16px;margin-top: 16px">
                    <el-col :span="12">
                        <span>
                            <i> <img src="./global/image/phone.png" width="14px"></i>
                            手机:
                        </span>
                        <span>134432333333</span>
                    </el-col>
                    <el-col :span="12">
                         <span>
                            <i> <img src="./global/image/rili.png" style="width: 14px"></i>
                            生日:
                        </span>
                        <span>1995-04-28</span>
                    </el-col>
                </el-row>
                <hr style="border: none; height: 1px; color: rgb(232, 232, 232); background-color: rgb(232, 232, 232); margin-bottom: 32px;">
                <el-row style="margin-bottom: 16px">
                    <el-col :span="12">
                        <span> 部门:</span>
                        <span>1995-04-28</span>
                    </el-col>
                    <el-col :span="12">
                        <span> 职务:</span>
                        <span>1995-04-28</span>
                    </el-col>
                </el-row>
            </el-card>
        </el-main>
        </el-container>
    `,
    data(){

    },
    methods:{

    },

})