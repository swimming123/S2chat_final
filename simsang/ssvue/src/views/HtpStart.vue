/*UI개발: 송지미 */
<template>
    <div>
        <div class="main">
            <div class="container-xxl py-5">
                <div class="container">
                    <div class="position-relative overflow-hidden">
                        <div class="sub_htp_content">
                            <div class="sub_htp01" style="background:url(../images/sub_htp01bg.png) center center;margin-left:0; margin-bottom: 40px;">
                                <p class="sub_htp01_font"><span>HTP 검사</span></p>
                                <div class="sub_sub_htp01">
                                    <span>H(House), T(Tree), P(Person) 세가지 항목 검사를 통해 내면의 심리상태를 보다 자세히 알아볼 수 있는 검사입니다.</span>
                                </div>
                            </div>
                            <!--검사항목 선택 문구-->
                            <div class="sub_htp_con">
                                <div class="sub_htp_content2">
                                    <div class="htp_start_font" style="margin-bottom: 18px;">검사 항목 선택</div>
                                    <p class="sub_sub_htp02" style="margin-bottom: 18px;">받고자하는 HTP 테스트를 선택해주세요!</p>
                                    <div class="htp_start_bar" style="margin-bottom: 60px;"></div>

                                    <!--HTP_Test 4가지 항목-->
                                    <div class="htp_start01">
                                        <ul class="htp_list">
                                            <li><p><router-link :to="{ name: 'HtpDrawing', params: { type: 'house' }}"><img src="/images/htp_01_house.png"><span>테스트 시작</span></router-link></p></li>
                                            <li><p><router-link :to="{ name: 'HtpDrawing', params: { type: 'tree' }}"><img src="/images/htp_02_tree.png"><span>테스트 시작</span></router-link></p></li>	
                                            <li><p><router-link :to="{ name: 'HtpDrawing', params: { type: 'men' }}"><img src="/images/htp_men.png"><span>테스트 시작</span></router-link></p></li>
                                            <li><p><router-link :to="{ name: 'HtpDrawing', params: { type: 'female' }}"><img src="/images/htp_women.png"><span>테스트 시작</span></router-link></p></li>	
                                            <!-- <li><p><router-link :to="{ name: 'HtpDrawingSet' }"><img src="/images/htp_04_package.png"><span>테스트 시작</span></router-link></p></li>	 -->
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div> 
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: 'HtpStart'
}
</script>

<style>
.container-xxl, .container {
    width: 100%;
    max-width: 100%;
    padding: 0 15px;
    margin: 0 auto;
}

.htp_start01 {
    display: flex;
    justify-content: center;
    width: 100%;
    margin: 0 auto;
}

.sub_htp01 {
    width: 100%;
    background: url('/public/images/sub_htp01bg.png') center center;
    margin-left: 0;
    margin-bottom: 40px;
    margin-top: 20px; 
    height: 220px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
}

.sub_htp01_font {
    font-size: 24px;
    color: white; 
    text-align: center;
}
.sub_sub_htp01{
    color: white;
    text-align: center;
    font-size: 15px;
    margin-top: 10px;
}

.htp_list li p a {
    display: block;
    text-align: center;
    text-decoration: none;
    color: white;
}

.htp_list li p a img {
    display: block;
    width: 100%;
    height: auto;
}

.htp_list li p a span {
    display: block;
    background-color: #2BA8D9;
    padding: 10px 0; 
    border-radius: 4px; 
    margin-top: 10px;
    width: 100%; 
    box-sizing: border-box; 
}

.htp_list li p a:hover span {
    background-color: #ca347a; 
    transition: background-color 0.3s; 
}

.htp_list {
	display: flex;
	justify-content: space-between;
	padding: 0;
	list-style: none;
    width: 900px; 
}

.htp_list li {
	flex: 1;
	margin: 0 10px;
	text-align: center;
}

.sub_sub_htp02{
    text-align: center;
    font-size: 15px;
}

.sub_htp_content2 {
	text-align: center;
}

.htp_start_font {
    color: #2BA8D9;
    font-weight: bold;
    font-size: 20px;
}

.htp_start_bar {
    background-color: #2BA8D9;
    height: 2px; 
    width: 10%; 
    margin: 10px auto;
}
</style>
