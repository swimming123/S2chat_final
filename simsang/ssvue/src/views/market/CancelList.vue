/*UI개발:이승희*/
<template>
  <div class="mypage">
    <div class="contact_area">
      <div class="text-center mt-5">
        <h3 style="color: dimgrey">취소목록</h3>
        <hr style="border-color: gray" />
      </div>
      <div v-if="orders.length === 0">취소된 주문이 없습니다.</div>
      <div class="row w-100 m-auto align-items-center d-flex">
        <div
          class="card-body d-flex justify-content-center"
          v-for="order in orders"
          :key="order.id"
        >
          <div @click="selectOrder(order.ordernum)" class="card p-3 col-md-9">
            <router-link
              :to="{ name: 'CancelDetail', query: { id: order.id } }"
            >
              <img
                :src="getImageUrl(order.img)"
                class="img-responsive"
                width="80"
                height="80"
                alt=""
                @error="setDefaultImage($event)"
              />
            </router-link>
            <span class="order-description">
              <router-link
                :to="{ name: 'CancelDetail', query: { id: order.id } }"
              >
                <p>{{ order.name }}</p>
              </router-link>
              <p>{{ order.price }}원</p>
            </span>

            <p>
              {{ order.odate }} 처리상태 |
              {{ order.delivery }}
            </p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      membernum: localStorage.getItem("membernum"),
      orders: [], // Fetch orders here
      selectedOrder: null, // 선택된 주문을 저장할 상태
    };
  },
  mounted() {
    this.customerOrderList();
  },
  methods: {
    selectOrder(order) {
      this.$router.push({
        name: "CancelDetail",
        query: { "order": order},
      });
    },
    customerOrderList() {
      axios
        .post(
          `${process.env.VUE_APP_BACK_END_URL}/order/cancellist/${this.membernum}`
        )
        .then((resp)=>this.orders =resp.data)
        .catch((error) => {
          console.error("Error confirming cancel:", error);
        });
    },
    getImageUrl(product_image) {
      if (!product_image) {
        return `/images/product/maketadd_default.png`;
      }
      const imageUrl = `${process.env.VUE_APP_BACK_END_URL}/uploads/${product_image}`;
      return imageUrl;
    },
    setDefaultImage(event) {
      event.target.src = "/images/noimg.png"; // Path to your default image
    },
  },
};
</script>
