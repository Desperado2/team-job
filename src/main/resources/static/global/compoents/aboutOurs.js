var abour_ours = Vue.component('about-ours',{
    template:`
        <el-container>
            <el-header style="height: 80px">
            <div style="background-color: white;height: 140px;margin-top: 10px">
                <div style="height: 30px;width: 100%;margin-top: 30px">
                    <a href="#" @click="addUser"><h4 style="color: #ef9b6c">+添加成员</h4></a>
                </div>
            </div>
        </el-header>
        <el-main class="index-el-main">
            <el-card class="box-card">
                <el-row :gutter="20" v-for="userList in users" style="margin-bottom: 50px">
                    <el-col :span="6" v-for="user in userList">
                        <a href="#" @click="lookUserInfo(user.id)">
                            <div style="text-align: center">
                                <img :src="user.headUrl" style="width: 100px;border-radius: 10px">
                                <div v-text="user.name"></div>
                            </div>
                        </a>
                    </el-col>
             
                </el-row>
                
            </el-card>
        </el-main>
        </el-container>
    `,
    data(){
        return{
            typeSelect:this.optionsCode,
            users:[]
        }
    },
    mounted:function(){
        let _this = this;
        axios({
            method: 'get',
            url: 'users',
        }).then(function (result) {
            console.log(result)
            if (result.data.success){
                _this.users = result.data.data;
            }else {
                _this.$message({
                    message:result.data.msg,
                    type:'error'
                });
            }
        })
    },
    props:['optionsCode','userId'],
    methods:{
        lookUserInfo(name){
            this.typeSelect = 'memberInfo'
            Bus.$emit("userId",name);
            Bus.$emit("optionsCode",this.typeSelect);
        },
        addUser:function () {
            this.typeSelect = 'addUser'
            Bus.$emit("optionsCode",this.typeSelect);
        }
    },

})