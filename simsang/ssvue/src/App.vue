<template>
  <div>
    <pageHeader />   <!-- 컴포넌트 태그는 자동으로 '-'로 변환되어 인식됩니다 -->
    <router-view />
    <pageFooter />
  </div>
</template>
<script>
import pageFooter from './components/pageFooter.vue';
import pageHeader from './components/pageHeader.vue';

export default {
  name: 'App',
  components: {
    pageHeader,  
    pageFooter
  },
}
</script>