/*UI개발:이승희*/
<template>
  <div class="mypage">
    <div class="contact_area">
      <div class="text-center mt-5 w-100">
        <h3 style="color: dimgrey">배송지목록</h3>
        <div style="display: grid; justify-content: end">
          <router-link to="/AddressAdd"><button>배송지 추가하기</button></router-link>
        </div>
      </div>
      <hr style="border-color: gray" />
      <div class="text-center">
        <div class="card-body" v-if="addresslist.length === 0">
          데이터가 없습니다.
        </div>

        <div
          class="card-body box m-4"
          v-for="address in addresslist"
          :key="address.num"
        >
          <p>주소별명 : {{ address.addressnick }}</p>
          <p>주소 : {{ address.address }}</p>
          <p>상세주소 : {{ address.detailaddress }}</p>
          <p>요청사항 : {{ address.addrequest }}</p>
          <button @click="addressupdate(address.num)">수정</button>

          <button @click="addressdelete(address.num)">삭제</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import AddressAdd from "./AddressAdd.vue";

export default {
  data() {
    return {
      membernum: localStorage.getItem("membernum"),
      addresslist: [],
      selectedOrder: null,
      showModal: false, // 초기값이 false로 설정되어야 함
    };
  },
  created() {
    this.addressList();
  },
  components: {
    AddressAdd,
  },
  methods: {
    addressList() {
      axios
        .post(`${process.env.VUE_APP_BACK_END_URL}/address/list`, null, {
          params: { membernum: this.membernum },
        })
        .then((resp) => {
          this.addresslist = resp.data;
        })
        .catch((error) => {
          console.error("Error fetching orders:", error);
        });
    },
    addressupdate(addressnum) {
      this.$router.push({
        name: "AddressAdd",
        query: { addressnum: addressnum },
      });
    },
    addressdelete(addressnum) {
      axios
        .post(`${process.env.VUE_APP_BACK_END_URL}/address/delete`, null, {
          params: { addressnum: addressnum },
        })
        .then(() => {
          this.addressList();
          alert("삭제되었습니다.");
        })
        .catch((error) => console.error(error));
    },
  },
};
</script>
