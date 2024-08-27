/*UI개발:이승희*/
<template>
  <div class="main">
    <div class="container">
      <div class="row justify-content-center align-items-center">
        <h1 class="text-center mb-4 mt-5">배송지 추가하기</h1>
        <p style="text-align: right; margin-top: 30px; font-size: small">
          <!--  필수입력사항 -->
        </p>
        <hr style="border-color: gray" />
        <div class="col-md-6 col-xl-4">
          <label for="inputID" class="col-xs-3">배송지 별명</label>
          <div class="col-xs-6">
            <input
              type="text"
              class="form-control"
              id="nick"
              placeholder="배송지 명칭을 입력하세요"
              v-model="address.addressnick"
            />
          </div>

          <label for="address">주소</label>
          <div class="form-group d-flex align-items-center">
            <input
              type="text"
              class="form-control"
              id="address"
              placeholder="주소를 입력하세요."
              v-model="address.address"
              maxlength="10"
              readonly
            />

            <button
              class="btn btn-primary"
              type="button"
              style="white-space: nowrap"
              @click="openAddressModal"
            >
              주소 검색
            </button>
          </div>

          <div v-if="address.address">
            <label for="inputID" class="col-xs-3">상세주소</label>
            <div class="col-xs-6">
              <input
                type="text"
                class="form-control"
                id="detailaddress"
                placeholder="상세 주소를 입력하세요"
                v-model="address.detailaddress"
              />
            </div>
            <label for="inputID" class="col-xs-3">배송 요청사항</label>
            <div class="col-xs-6">
              <input
                type="text"
                class="form-control"
                id="addrequest"
                placeholder="배송 요청사항을 입력하세요"
                v-model="address.addrequest"
              />
            </div>
          </div>

          <!-- 모달 컴포넌트 -->
          <address-modal
            v-if="isAddressModalOpen"
            @close="isAddressModalOpen = false"
            @select-address="selectAddress"
          ></address-modal>

          <div class="text-center my-3">
            <button
              v-if="addressnum"
              class="btn btn-primary"
              type="button"
              @click="addressupdate"
            >
              수정
            </button>
            <button
              v-else
              class="btn btn-primary"
              type="button"
              @click="addressadd"
            >
              추가
            </button>
            <button class="btn btn-primary" type="button" @click="goBack()">
              돌아가기
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import AddressModal from "../AddressModal.vue";

export default {
  data() {
    return {
      isAddressModalOpen: false,
      membernum: localStorage.getItem("membernum"),
      selectedAddress: "",
      addressnum: "",
      address: {
        addressnick: "",
        address: "",
        detailaddress: "",
        addrequest: "",
      },
    };
  },
  components: {
    AddressModal,
  },
  created() {
    this.addressnum = this.$route.query.addressnum;
    if (this.addressnum) {
      this.updatereload();
    }
    // Vue Router의 afterEach를 사용하여 스크롤을 페이지 상단으로 이동
    
  },
  mounted() {
    if (!localStorage.getItem("reloaded")) {
      localStorage.setItem("reloaded", "true");
      window.location.reload(true);
    } else {
      localStorage.removeItem("reloaded");
    }
    this.$nextTick(() => {
      window.scrollTo(0, 0); // 또는 저장된 스크롤 위치
    });
  },
  methods: {
    openAddressModal() {
      this.isAddressModalOpen = true;
    },
    selectAddress(address) {
      this.address.address = address;
      this.isAddressModalOpen = false;
    },
    goBack() {
      window.scrollTo(0, 0); 
      this.$router.go(-1); 
    },
    updatereload() {
      axios
        .post(`${process.env.VUE_APP_BACK_END_URL}/address/detail`, null, {
          params: { addressnum: this.addressnum },
        })
        .then((resp) => {
          this.address = resp.data;
        })
        .catch((error) => console.error("Error updating address:", error));
    },
    addressupdate() {
      if (
        !this.address.address ||
        !this.address.addrequest ||
        !this.address.detailaddress ||
        !this.address.addressnick
      ) {
        alert("모든 사항을 입력해주세요");
        return;
      }
      const formdata = new FormData();
      formdata.append("num", this.address.num);
      formdata.append("membernum", this.membernum);
      formdata.append("address", this.address.address);
      formdata.append("addressnick", this.address.addressnick);
      formdata.append("detailaddress", this.address.detailaddress);
      formdata.append("addrequest", this.address.addrequest);
      axios
        .post(`${process.env.VUE_APP_BACK_END_URL}/address/update`, formdata)
        .then((res) => {
          alert("성공적으로 수정되었습니다.");
          this.goBack();
        })
        .catch((error) => console.error("Error updating address:", error));
    },
    addressadd() {
      if (
        !this.address.address ||
        !this.address.addrequest ||
        !this.address.detailaddress ||
        !this.address.addressnick
      ) {
        alert("모든 사항을 입력해주세요");
        return;
      }
      const formdata = new FormData();
      formdata.append("membernum", this.membernum);
      formdata.append("address", this.address.address);
      formdata.append("addressnick", this.address.addressnick);
      formdata.append("detailaddress", this.address.detailaddress);
      formdata.append("addrequest", this.address.addrequest);
      axios
        .post(`${process.env.VUE_APP_BACK_END_URL}/address/add`, formdata)
        .then((res) => {
          if (res.status == 200) {
            alert("배송지가 추가되었습니다.");
            this.goBack();
          }
        })
        .catch((error) => alert("배송지가 추가에 오류가 발생헀습니다."));
    },
  },
};
</script>