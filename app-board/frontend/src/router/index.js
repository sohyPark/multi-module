import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import Login from '../components/Login'
import Post from "../components/Post";
import HackerNews from "../components/HackerNews";
import Setting from "../components/Setting";
import User from "../components/User";
import Board from "../components/Board";

import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'


Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'HelloWorld',
      component: HelloWorld
    },
    {
      path: '/login',
      name: Login,
      component: Login,
      props: true
    },
    {
      path: '/post',
      name: Post,
      component: Post,
      props: true
    },
    {
      path: '/board',
      name: Board,
      component: Board,
      props: true
    },
    {
      path: '/news',
      name: HackerNews,
      component: HackerNews,
      props: true
    },
    {
      path: '/setting',
      name: Setting,
      component: Setting,
      children: [
        {
          path: 'user',
          component: User
        },
        {
          path: 'board',
          component: Board
        }
      ],
      props: true
    }
  ]
})
