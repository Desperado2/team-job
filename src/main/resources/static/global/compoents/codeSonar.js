var code_sonar = Vue.component('code-sonar',{
    template:`
        <el-container>
            <el-header style="height: 80px">
            <div style="background-color: white;height: 140px;margin-top: 10px">
                <div style="height: 30px;width: 100%;margin-top: 30px">
                    <h3>代码质量</h3>
                </div>
            </div>
        </el-header>
        <el-main class="index-el-main">
            <el-row :gutter="20" v-for="i in 6" :key="i" style="margin-bottom: 20px">
                <el-col :span="8">
                    <el-card class="box-card">
                        <div >
                            <el-container>
                                <el-aside width="60px">
                                    <img src="./global/image/sonar.png" style="width: 48px">
                                </el-aside>
                                <el-main style="padding:0">
                                    <div>缺陷数</div>
                                    <div style="font-size: 20px">55</div>
                                </el-main>
                                <el-aside width="60px" style="text-align: right">
                                    <img style="width: 14px" src="./global/image/tip-4.png" >
                                </el-aside>
                            </el-container>

                            <el-row :gutter="20" style="margin-top: 10px;border-bottom: 1px solid gainsboro">
                                <el-col :span="12"><div style="text-align: left"><img src="./global/image/bad_taste.png" style="width: 14px">坏味道:123个</div></el-col>
                                <el-col :span="12"><div style="text-align: left"><img src="./global/image/repeat_row.png" style="width: 14px">重复行数:2345行</div></el-col>
                            </el-row>
                         <div style="margin-top: 10px">客户管理系统</div>
                        </div>
                    </el-card>
                </el-col>
                <el-col :span="8">
                    <el-card class="box-card">
                        <div >
                            <el-container>
                                <el-aside width="60px">
                                    <img src="./global/image/sonar.png" style="width: 48px">
                                </el-aside>
                                <el-main style="padding:0">
                                    <div>缺陷数</div>
                                    <div>55</div>
                                </el-main>
                                <el-aside width="60px" style="text-align: right">
                                    <img style="width: 14px" src="./global/image/tip-4.png" >
                                </el-aside>
                            </el-container>
                            <el-row :gutter="20" style="margin-top: 10px;border-bottom: 1px solid gainsboro">
                                <el-col :span="12"><div style="text-align: left"><img src="./global/image/bad_taste.png" style="width: 14px">坏味道:123个</div></el-col>
                                <el-col :span="12"><div style="text-align: left"><img src="./global/image/repeat_row.png" style="width: 14px">重复行数:2345行</div></el-col>
                            </el-row>
                            <div style="margin-top: 10px">客户管理系统</div>
                        </div>
                    </el-card>
                </el-col>
                <el-col :span="8">
                    <el-card class="box-card">
                        <div >
                            <el-container>
                                <el-aside width="60px">
                                    <img src="./global/image/sonar.png" style="width: 48px">
                                </el-aside>
                                <el-main style="padding:0">
                                    <div>缺陷数</div>
                                    <div>55</div>
                                </el-main>
                                <el-aside width="60px" style="text-align: right">
                                    <img style="width: 14px" src="./global/image/tip-4.png" >
                                </el-aside>
                            </el-container>
                            <el-row :gutter="20" style="margin-top: 10px;border-bottom: 1px solid gainsboro">
                                <el-col :span="12"><div style="text-align: left"><img src="./global/image/bad_taste.png" style="width: 14px">坏味道:123个</div></el-col>
                                <el-col :span="12"><div style="text-align: left"><img src="./global/image/repeat_row.png" style="width: 14px">重复行数:2345行</div></el-col>
                            </el-row>
                            <div style="margin-top: 10px">客户管理系统</div>
                        </div>
                    </el-card>
                </el-col>
            </el-row>
        </el-main>
        </el-container>
    `,
    data(){
        return{

        }
    },
    methods:{

    },

})