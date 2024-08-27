import { createRouter, createWebHistory } from "vue-router";

import DiaryResultView from "@/views/DiaryResultView.vue";
import DiaryTalkView from "@/views/DiaryTalkView.vue";

import HtpDrawing from "@/views/HtpDrawing.vue";
import HtpStart from "@/views/HtpStart.vue";
import Loading from "@/views/Loading.vue";
import Qnadetail from "@/views/Qnadetail.vue";
import Qnainfo from "@/views/Qnainfo.vue";
import Qnalist from "@/views/Qnalist.vue";
import Qnaup from "@/views/Qnaup.vue";
import Qnawrite from "@/views/Qnawrite.vue";
import Require from "@/views/Require.vue";
import Returns from "@/views/Returns.vue";
import SimpleChat from "@/views/SimpleChat.vue";
import SimpleSurveyStart from "@/views/SimpleSurveyStart.vue";
import SimpleTalk from "@/views/SimpleTalk.vue";
import Svcinfo from "@/views/Svcinfo.vue";
import Turnjs from "@/views/Turnjs.vue";
import DiaryTextView from "../views/DiaryTextView.vue";
import DiaryViews from "../views/DiaryViews.vue";
import DiaryViews_Choice from "../views/DiaryViews_Choice.vue";
import DiaryViews_Date from "../views/DiaryViews_Date.vue";
import DiaryViews_Emotion from "../views/DiaryViews_Emotion.vue";
import DiaryViews_Graph from "../views/DiaryViews_Graph.vue";
import HtpResultLoding from "../views/HtpResultLoding.vue";
import HtpTestResult from "../views/HtpTestResult.vue";
import Login from "../views/Login.vue";
import Main from "../views/Main.vue";
import MyPageModal from "../views/MyPageModal.vue";
import MyPageView from "../views/MyPageView.vue";
import OpenChat from "../views/OpenChat.vue";
import OpenChatCheck from "../views/OpenChatCheck.vue";
import OpenChatList from "../views/OpenChatList.vue";

import Sign from "../views/SignUp.vue";

import SimpleResult from "../views/SimpleResult.vue";
import SimpleResultLoading from "../views/SimpleResultLoading.vue";
import SimulationCategory from "../views/simulation/SimulationCategory.vue";
import SimulationChat from "../views/simulation/SimulationChat.vue";
import SimulationIntro from "../views/simulation/SimulationIntro.vue";
import SimulationResult from "../views/simulation/SimulationResult.vue";
import SimulationResultLoading from "../views/simulation/SimulationResultLoading.vue";
import SimulationType from "../views/simulation/SimulationType.vue";
import SimulationVideo from "../views/simulation/SimulationVideo.vue";

import FindId from "@/views/FindId.vue";
import FindIdCheck from "@/views/FindIdCheck.vue";
import FindPwd from "@/views/FindPwd.vue";
import FindPwdCheck from "@/views/FindPwdCheck.vue";

import MyPageKakao from "../views/MyPageKakao.vue";
import MyPageTurn from "../views/MyPageTurn.vue";


//ictmarket
import AddressAdd from "../views/market/AddressAdd.vue";
import AllergicTest from "../views/market/AllergicTest.vue";
import CancelDetail from "../views/market/CancelDetail.vue";
import CartView from "../views/market/CartView.vue";
import Favorite from "../views/market/Favorite.vue";
import MarketHome from "../views/market/MarketHomeView.vue";
import OrderCancel from "../views/market/OrderCancel.vue";
import OrderDetail from "../views/market/OrderDetail.vue";
import PaymentComplete from "../views/market/PayComplete.vue";
import PaymentAdd from "../views/market/PaymentAdd.vue";
import PayView from "../views/market/PayView.vue";
import ProductDetail from "../views/market/ProductDetail.vue";
import ProductListView from "../views/market/ProductListView.vue";

import JimiProductAdd from "@/views/market/JimiProductAdd.vue";
import JimiProductDetail from "@/views/market/JimiProductDetail.vue";
import JimiProductList from "@/views/market/JimiProductList.vue";
import JimiReviewAdd from "@/views/market/JimiReviewAdd.vue";
import JimiReviewDetail from "@/views/market/JimiReviewDetail.vue";
import JimiReviewList from "@/views/market/JimiReviewList.vue";


//ictmarket
import MarketQnadetail from '@/views/market/MarketQnadetail.vue'; // 마켓 문의 상세보기 컴포넌트
import MarketQnainfo from '@/views/market/MarketQnainfo.vue'; // 마켓 문의 정보 컴포넌트
import MarketQnaup from '@/views/market/MarketQnaup.vue'; // 마켓 문의 수정 컴포넌트
import MarketQnawrite from '@/views/market/MarketQnawrite.vue'; // 마켓 문의 작성 컴포넌트
import DiaryAdd from "@/views/DiaryAdd.vue";


const routes = [
  {
    path: "/",
    name: "Main",
    component: Main,
  },
  {
    path: "/Qnainfo",
    name: "Qnainfo",
    component: Qnainfo,
  },
  {
    path: "/Qnawrite",
    name: "Qnawrite",
    component: Qnawrite,
  },
  {
    path: "/Qnaup/:num",
    name: "Qnaup",
    component: Qnaup
  },
  {
    path: "/Qnalist",
    name: "Qnalist",
    component: Qnalist
  },
  {
    path: "/Qnadetail/:num",
    name: "Qnadetail",
    component: Qnadetail
  },
  {
    path: "/Svcinfo",
    name: "Svcinfo",
    component: Svcinfo
  },
  {
    path: "/Require",
    name: "Require",
    component: Require
  },
  {
    path: "/Returns",
    name: "Returns",
    component: Returns
  },
  {
    path: "/Loading",
    name: "Loading",
    component: Loading
  },
  {
    path: "/Login",
    name: "Login",
    component: Login
  },
  {
    path:'/FindId',
    name:'FindId',
    component: FindId
  },
  {
    path:'/FindPwd',
    name:'FindPwd',
    component: FindPwd
  },
  {
    path:'/FindIdCheck/:id/:mdate',
    name:'FindIdCheck',
    component: FindIdCheck
  },
  {
    path:'/FindPwdCheck/:email',
    name:'FindPwdCheck',
    component: FindPwdCheck
  },
  {
    path: "/Sign",
    name: "Sign",
    component: Sign
  },
  {
    path: "/SimpleSurveyStart",
    name: "SimpleSurveyStart",
    component: SimpleSurveyStart
  },
  {
    path: "/Schat",
    name: "Schat",
    component: SimpleChat
  },
  {
    path: "/Stalk",
    name: "Stalk",
    component: SimpleTalk
  },
  {
    path: "/SimpleResult",
    name: "SimpleResult",
    component: SimpleResult
  },
  {
    path: "/SimpleResultLoading",
    name: "SimpleResultLoading",
    component: SimpleResultLoading
  },
  {
    path: '/HtpStart', // HTP_start : HTP 검사 항목 선택 페이지
    name:'HtpStart',
    component : HtpStart
  },
  {
    path: '/HtpTestResult',  // HTP_Test 검사 결과지 (단일검사)
    name:'HtpTestResult',
    component : HtpTestResult
  },
  {
    path: '/HtpTestResult/:type',  // HTP_Test 검사 결과지 (단일검사)
    name:'HtpTestResult',
    component : HtpTestResult
  },
  {
    path: '/HtpDrawing/:type',  // HTP_Test 그림그리기 페이지(단일 검사)
    name:'HtpDrawing',
    component : HtpDrawing,
  },
  {
    path: '/HtpResultLoding',  // HTP_Test 검사 결과지 (단일검사)
    name:'HtpResultLoding',
    component : HtpResultLoding
  },
  {
    path: '/SimulationIntro',
    name: 'SimulationIntro',
    component : SimulationIntro
  },
  {
    path: '/SimulationCategory',
    name: 'SimulationCategory',
    component : SimulationCategory
  },
  {
    path: '/SimulationType',
    name: 'SimulationType',
    component : SimulationType
  },
  {
    path: '/SimulationVideo',
    name: 'SimulationVideo',
    component : SimulationVideo
  },
  {
    path: '/SimulationChat',
    name: 'SimulationChat',
    component : SimulationChat
  },
  {
    path: '/SimulationResultLoading',
    name: 'SimulationResultLoading',
    component : SimulationResultLoading
  },
  {
    path: '/SimulationResult',
    name: 'SimulationResult',
    component : SimulationResult
  },
  {
    path: "/OpenChatList",
    name: "OpenChatList",
    component : OpenChatList
  },
  {
    path: "/OpenChatCheck",
    name: "OpenChatCheck",
    component : OpenChatCheck
  },
  {
    path: "/OpenChat",
    name: "OpenChat",
    component : OpenChat
  },
  {
    path: "/diaryviews",
    name: "DiaryViews",
    component : DiaryViews
  },
  {
    path: "/diaryViews_choice",
    name: "DiaryViews_Choice",
    component : DiaryViews_Choice
  },
  {
    path: "/diaryViews_emotion",
    name: "DiaryViews_Emotion",
    component : DiaryViews_Emotion
  },
  {
    path: "/diaryViews_date",
    name: "DiaryViews_Date",
    component : DiaryViews_Date
  },
  {
    path: "/diaryViews_graph",
    name: "DiaryViews_Graph",
    component : DiaryViews_Graph
  },
  {// 대화형 일기장 
    path:'/DiaryTalkView',
    name:'DiaryTalkView',
    component: DiaryTalkView
  },
    {//텍스트형 일기장 
      path:'/DiaryTextView',
      name:'DiaryTextView',
      component: DiaryTextView
    },
    {
      path:"/DiaryResultView",
      name:'DiaryResultView',
      component: DiaryResultView
    },
    {
      path:"/DiaryAdd",
      name:"DiaryAdd",
      component:DiaryAdd
    },
     {//마이페이지
      path:'/mypageview',
      name:'MyPageView',
      component: MyPageView
    },
    {
      path:'/mypagemodal',
      name:'MyPageModal',
      component: MyPageModal
    },
    {
      path:'/Turnjs',
      name:'Turnjs',
      component: Turnjs
    },
    {
      path:'/MyPageKakao',
      name:'MyPageKakao',
      component: MyPageKakao
    },
    {
      path:'/MyPageTurn',
      name:'MyPageTurn',
      component: MyPageTurn
    },
    
    {
      path:'/MyPageKakao',
      name:'MyPageKakao',
      component: MyPageKakao
    },
    {
      path:'/MyPageTurn',
      name:'MyPageTurn',
      component: MyPageTurn
    },
      //ICT마켓 시작
      {
        path:'/MarketHome',
        name:'MarketHome',
        component: MarketHome
      },
      {
        path:'/Favorite',
        name:'Favorite',
        component: Favorite
      },
      {
        path:'/CartView',
        name:'CartView',
        component: CartView
      },
      {
        path:'/AllergicTest',
        name:'AllergicTest',
        component: AllergicTest
      },
      {
        path:'/ProductListView',
        name:'ProductListView',
        component: ProductListView
      },
      {
        path:'/ProductDetail',
        name:'ProductDetail',
        component: ProductDetail
      },
      {
        path:'/PayView',
        name:'PayView',
        component: PayView
      },
      {
        path:'/OrderDetail/:id',
        name:'OrderDetail',
        component: OrderDetail
      },
      {
        path:'/OrderCancel',
        name:'OrderCancel',
        component: OrderCancel
      },
      {
        path: "/PaymentComplete",
        name: "PaymentComplete",
        component : PaymentComplete
      },
      {
        path: "/AddressAdd",
        name: "AddressAdd",
        component : AddressAdd
      },
      {
        path: "/PaymentAdd",
        name: "PaymentAdd",
        component : PaymentAdd
      },
      {
        path: "/JimiProductAdd",
        name: "JimiProductAdd",
        component : JimiProductAdd
      },
      {
        path: "/JimiProductDetail:product_num",
        name: "JimiProductDetail",
        component : JimiProductDetail
      },
      {
        path: "/JimiProductList",
        name: "JimiProductList",
        component : JimiProductList
      },
      { // 리뷰리스트
        path:'/JimiReviewList/',
        name:'JimiReviewList',
        component:JimiReviewList
      },
      { 
        // 리뷰상세
        path:'/JimiReviewDetail/',
        name:'JimiReviewDetail',
        component:JimiReviewDetail
      },
      { // 리뷰등록
        path:'/JimiReviewAdd/',
        name:'JimiReviewAdd',
        component:JimiReviewAdd
      },  
      {
        path: "/CancelDetail",
        name: "CancelDetail",
        component : CancelDetail
      },
    {
        path: '/MarketQnawrite',
        name: 'MarketQnawrite',
        component: MarketQnawrite,
    },
    {
        path: '/MarketQnaup/:num',
        name: 'MarketQnaup',
        component: MarketQnaup,
        props: true, // URL 파라미터를 컴포넌트 props로 전달
    },
    {
        path: '/MarketQnadetail/:num',
        name: 'MarketQnadetail',
        component: MarketQnadetail,
        props: true, // URL 파라미터를 컴포넌트 props로 전달
    },
    {
        path: '/MarketQnainfo',
        name: 'MarketQnainfo',
        component: MarketQnainfo,
        props: true, // URL 파라미터를 컴포넌트 props로 전달
    },
      
      //ICT마켓 끝

];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;