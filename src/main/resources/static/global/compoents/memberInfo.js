var member_info = Vue.component('member-info',{
    template:`
        <el-container>
        <el-header style="height: 40px">
           
        </el-header>
        <el-main class="index-el-main">
            <el-card class="box-card" style="font-size: 14px">
                <el-row style="margin-bottom: 16px">
                    <el-col :span="24">
                        <img :src="user.headUrl" width="100px" style="border-radius: 10px">
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12">
                        <span>
                            <i> <img src="./global/image/user.png" width="14px"></i>
                            昵称:
                        </span>
                        <span v-text="user.name"></span>
                    </el-col>
                    <el-col :span="12">
                        <span>
                            <i> <img src="./global/image/mail.png" width="14px"></i>
                            邮箱:
                        </span>
                        <span v-text="user.email"></span>
                    </el-col>
                </el-row>
                <el-row style="margin-bottom: 16px;margin-top: 16px">
                    <el-col :span="12">
                        <span>
                            <i> <img src="./global/image/phone.png" width="14px"></i>
                            手机:
                        </span>
                        <span v-text="user.phone"></span>
                    </el-col>
                    <el-col :span="12">
                         <span>
                            <i> <img src="./global/image/rili.png" style="width: 14px"></i>
                            生日:
                        </span>
                        <span >{{user.birthType}} | {{ user.birthday | dataFormat('yyyy-MM-dd')}}</span>
                    </el-col> 
                </el-row>
                <hr style="border: none; height: 1px; color: rgb(232, 232, 232); background-color: rgb(232, 232, 232); margin-bottom: 32px;">
                <el-row style="margin-bottom: 16px">
                    <el-col :span="12">
                        <span> 部门:</span>
                        <span v-text="user.department"></span>
                    </el-col>
                    <el-col :span="12">
                        <span> 职务:</span>
                        <span v-text="user.position"></span>
                    </el-col>
                </el-row>
            </el-card>
        </el-main>
        </el-container>
    `,
    data(){
        return{
            user:{
                id:'',
                name: '',
                email: '',
                password: '',
                phone : '',
                birthday: '',
                birthType: '',
                department: '',
                position: '',
                headUrl:''
            },
            id : this.userId
        }
    },
    mounted:function(){
        let _this = this;
        axios({
            method: 'get',
            url: 'users/'+_this.id,
        }).then(function (result) {
            if (result.data.success){
                _this.user = result.data.data;
            }else {
                _this.$message({
                    message:result.data.msg,
                    type:'error'
                });
            }
        })
    },
    props:['userId'],
    methods:{

    },
    filters: {
        formatDate(time) {
            var date = new Date(time);
            return formatDate(date, 'yyyy-MM-dd');
        }
    },
})