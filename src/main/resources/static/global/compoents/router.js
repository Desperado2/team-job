
Vue.use(VueRouter)

const Foo = {template:'<div>hello</div>'}

const routers = [
    {pass:'/indexComp',component: index_comp},
    {pass:'/weekReport',component: week_report},
    {pass:'/appManager',component: app_manager},
    {pass:'/code-sonar',component: code_sonar},
    {pass:'/project-date',component: project_date},
    {pass:'/about-ours',component: abour_ours},
    {pass:'/member-info',component: member_info},
    {pass:'/add-user',component: add_user},
    {pass:'/create-app',component: create_app},
    {pass:'/create-project',component: create_project},
    {pass:'/create-demand',component: create_demand},
]

const router = new VueRouter({
    routers
})
