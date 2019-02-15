var abour_ours = Vue.component('about-ours',{
    template:`
        <el-container>
            <el-header style="height: 80px">
            <div style="background-color: white;height: 140px;margin-top: 10px">
                <el-breadcrumb separator="/">
                    <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
                    <el-breadcrumb-item>关于我们</el-breadcrumb-item>
                </el-breadcrumb>
                <div style="height: 30px;width: 100%;margin-top: 30px">
                    <a href="#" @click=""><h4 style="color: #ef9b6c">+添加成员</h4></a>
                </div>
            </div>
        </el-header>
        <el-main class="index-el-main">
            <el-card class="box-card">
                <el-row :gutter="20">
                    <el-col :span="6">
                        <a href="#" @click="lookUserInfo('zz')">
                            <div style="text-align: center">
                                <img src="./image/tx/czm.jpg" style="width: 100px;border-radius: 10px">
                                <div>小陈</div>
                            </div>
                        </a>
                    </el-col>
                    <el-col :span="6">
                        <a href="#" @click="lookUserInfo('zz')">
                        <div style="text-align: center">
                            <img src="./image/tx/hb.jpg" style="width: 100px;border-radius: 10px">
                            <div>大黄</div>
                        </div>
                        </a>
                    </el-col>
                    <el-col :span="6">
                        <a href="#" @click="lookUserInfo('zz')">
                        <div style="text-align: center">
                            <img src="./image/tx/hj.png" style="width: 100px;border-radius: 10px">
                            <div>大军</div>
                        </div>
                        </a>
                    </el-col>
                    <el-col :span="6">
                        <a href="#" @click="lookUserInfo('zz')">
                        <div style="text-align: center">
                            <img src="./image/tx/hxj.jpg" style="width: 100px;border-radius: 10px">
                            <div>郝建</div>
                        </div>
                        </a>
                    </el-col>
                </el-row>
                <el-row :gutter="20" style="margin-top: 50px">
                    <el-col :span="6">
                        <a href="#" @click="lookUserInfo('zz')">
                        <div style="text-align: center">
                            <img src="./image/tx/xjk.jpg" style="width: 100px;border-radius: 10px">
                            <div>小胖</div>
                        </div>
                        </a>
                    </el-col>
                    <el-col :span="6">
                        <a href="#" @click="lookUserInfo('zz')">
                        <div style="text-align: center">
                            <img src="./image/tx/zyt.jpg" style="width: 100px;border-radius: 10px">
                            <div>大佬</div>
                        </div>
                        </a>
                    </el-col>
                    <el-col :span="6">
                        <a href="#" @click="lookUserInfo('zz')">
                        <div style="text-align: center">
                            <img src="./image/default.jpg" style="width: 100px;border-radius: 10px">
                            <div>小白</div>
                        </div>
                        </a>
                    </el-col>
                    <el-col :span="6">
                        <div style="text-align: center">
                            <img src="./image/default.jpg" style="width: 100px;border-radius: 10px">
                            <div>张三</div>
                        </div>
                    </el-col>
                </el-row>
                <el-row :gutter="20" style="margin-top: 50px">
                    <el-col :span="6">
                        <a href="#" @click="lookUserInfo('zz')">
                        <div style="text-align: center">
                            <img src="./image/tx/xjk.jpg" style="width: 100px;border-radius: 10px">
                            <div>小胖</div>
                        </div>
                        </a>
                    </el-col>
                    <el-col :span="6">
                        <a href="#" @click="lookUserInfo('zz')">
                        <div style="text-align: center">
                            <img src="./image/tx/zyt.jpg" style="width: 100px;border-radius: 10px">
                            <div>大佬</div>
                        </div>
                        </a>
                    </el-col>
                    <el-col :span="6">
                        <a href="#" @click="lookUserInfo('zz')">
                        <div style="text-align: center">
                            <img src="./image/default.jpg" style="width: 100px;border-radius: 10px">
                            <div>小白</div>
                        </div>
                        </a>
                    </el-col>
                    <el-col :span="6">
                        <a href="#" @click="lookUserInfo('zz')">
                        <div style="text-align: center">
                            <img src="./image/default.jpg" style="width: 100px;border-radius: 10px">
                            <div>张三</div>
                        </div>
                        </a>
                    </el-col>
                </el-row>
            </el-card>
        </el-main>
        </el-container>
    `,
    data(){

    },
    methods:{
        lookUserInfo(name){
            alert(name)
        }
    },

})