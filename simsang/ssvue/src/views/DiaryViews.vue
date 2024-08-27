/*UI개발: 박성호 */
<template>
  <div class="diaryViews">
    <ul class="nav nav-pills mb-3" id="pills-tab" role="tablist">
      <li class="nav-item" role="presentation">
        <button class="simulationIntro3" id="v-pills-heart-tab" type="button" @click="DiaryViews_Choice_Page">오늘</button>
        </li>
      <li class="nav-item" role="presentation">
        <button class="simulationIntro3" id="v-pills-heart-tab" type="button" @click="DiaryViews_Emotion_Page">감정</button>
        </li>
      <li class="nav-item" role="presentation">
        <button class="simulationIntro3" id="v-pills-date-tab" type="button" @click="DiaryViews_Date_Page">날짜</button>
      </li>
      <li class="nav-item" role="presentation">
        <button class="simulationIntro3" id="v-pills-graph-tab" type="button" @click="DiaryViews_Graph_Page">통계</button>
      </li>
    </ul>
    
    <br>

    <transition>
        <DiaryViews_Choice v-if="DiaryViews_Choice" />
    </transition>

    <transition>
        <DiaryViews_Emotion v-if="DiaryViews_Emotion" />
    </transition>

    <transition>
        <DiaryViews_Date v-if="DiaryViews_Date" />
    </transition>

    <transition>
        <DiaryViews_Graph v-if="DiaryViews_Graph" />
    </transition>
    

    </div>
</template>

<script>
import DiaryViews_Choice from '../views/DiaryViews_Choice.vue';
import DiaryViews_Emotion from '../views/DiaryViews_Emotion.vue';
import DiaryViews_Date from '../views/DiaryViews_Date.vue';
import DiaryViews_Graph from '../views/DiaryViews_Graph.vue';

export default {
  components: {
    DiaryViews_Choice,
    DiaryViews_Emotion,
    DiaryViews_Date,
    DiaryViews_Graph,
  },
  data() {
    return {
      DiaryViews_Choice: true,
      DiaryViews_Emotion: false,
      DiaryViews_Date: false,
      DiaryViews_Graph: false,
    };
  },
  methods: {
    DiaryViews_Choice_Page() {
      // 오늘 페이지를 표시하고 다른 페이지는 숨김
      this.DiaryViews_Choice = true;
      this.DiaryViews_Emotion = false;
      this.DiaryViews_Date = false;
      this.DiaryViews_Graph = false;
    },
    DiaryViews_Emotion_Page() {
      this.DiaryViews_Choice = false;
      this.DiaryViews_Emotion = true;
      this.DiaryViews_Date = false;
      this.DiaryViews_Graph = false;
    },
    DiaryViews_Date_Page() {
      this.DiaryViews_Choice = false;
      this.DiaryViews_Emotion = false;
      this.DiaryViews_Date = true;
      this.DiaryViews_Graph = false;
    },
    DiaryViews_Graph_Page() {
      this.DiaryViews_Choice = false;
      this.DiaryViews_Emotion = false;
      this.DiaryViews_Date = false;
      this.DiaryViews_Graph = true;
    },
  },
};
</script>

<style>
.DiaryViews_DatePicker{
  width: 700px;
}
.DiaryViews_page{
  margin: left;
}

.simulationIntro3 {
  margin-left: 100px;
  margin-right: 100px;
}
.diaryViews{
  margin: 150px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 20px;
}
.DiaryViews_Choice_views {
  background-color: #f8f9fa;
  padding: 1rem;
  border-radius: 5px;
}
</style>
