var project_date = Vue.component('project-date',{
    template:`
        <el-container>
           <el-header style="height: 140px">
            <div style="background-color: white;height: 160px;margin-top: 10px">
                <div style="height: 90px;width: 100%;margin-top: 30px">
                    <h3>项目排期</h3>
                    <span>每个有意义的项目都应该有个特别的代号。
                        <a href="#" style="color: #ef9b6c">创建项目排期  </a>
                        <a href="#" style="color: #ef9b6c">创建日常需求  </a>
                        <a href="#" style="color: #ef9b6c">预览项目排期</a>    </span>
                </div>
            </div>
        </el-header>
        <el-main class="index-el-main">
            <el-card class="box-card" style="margin-bottom: 20px">
                <div slot="header" class="clearfix">
                    <span>客户管理系统</span>
                    <el-button style="float: right; " type="success" @click="next(0)" plain>下一步</el-button>
                    <el-button style="float: right; " type="success" plain>编辑</el-button>
                </div>
                <el-steps :active="actives[0]" align-center finish-status="success">
                    <el-step title="需求评审" description="2019-02-18"></el-step>
                    <el-step title="接口评审" description="2019-02-28"></el-step>
                    <el-step title="测试" description="2019-03-23"></el-step>
                    <el-step title="预发" description="2019-04-28"></el-step>
                    <el-step title="发布" description="2019-05-28"></el-step>
                </el-steps>
            </el-card>

            <el-card class="box-card" style="margin-bottom: 20px">
                <div slot="header" class="clearfix">
                    <span>订单管理系统</span>
                    <el-button style="float: right; " type="success" @click="next(1)" plain>下一步</el-button>
                    <el-button style="float: right; " type="success"  plain>编辑</el-button>
                </div>
                <el-steps :active="actives[1]" align-center finish-status="success">
                    <el-step title="需求评审" description="2019-02-18"></el-step>
                    <el-step title="接口评审" description="2019-02-28"></el-step>
                    <el-step title="测试" description="2019-03-23"></el-step>
                    <el-step title="预发" description="2019-04-28"></el-step>
                    <el-step title="发布" description="2019-05-28"></el-step>
                </el-steps>
            </el-card>

            <el-card class="box-card" style="margin-bottom: 20px">
                <div slot="header" class="clearfix">
                    <span>客户营销系统</span>
                    <el-button style="float: right; " type="success" @click="next(2)" plain>下一步</el-button>
                    <el-button style="float: right; " type="success" plain>编辑</el-button>
                </div>
                <el-steps :active="actives[2]" align-center finish-status="success">
                    <el-step title="需求评审" description="2019-02-18"></el-step>
                    <el-step title="接口评审" description="2019-02-28"></el-step>
                    <el-step title="测试" description="2019-03-23"></el-step>
                    <el-step title="预发" description="2019-04-28"></el-step>
                    <el-step title="发布" description="2019-05-28"></el-step>
                </el-steps>
            </el-card>
        </el-main>

        </el-container>
    `,
    data(){
        return{
            actives: [3,1,4]
        }
    },
    methods:{
        next:function(item) {
            if (this.actives[item]++ > 4) {
                Vue.set(this.actives, item, 0)
            }else{
                Vue.set(this.actives, item, this.actives[item])
            };
        }
    },

})