<template>
  <div>
    <p style="font-size: 24px;color: #f00">hello,World</p>
    <p style="font-size: 24px;color: #f00">{{ code }}</p>
  </div>
</template>
<script>

export default {
  data() {
    return {
      code: ''
    };
  },
  mounted() {
    const params = window.location.hash
    const code = params.split('code=')[1]
    console.log('%c%s', 'color:#f00;font-size:22px;', "code:")
    console.log('%c%s', 'color:#fff;font-size:22px;background:#f0f', code)
    this.code = code
    // axios.get("/mtbase//oauthApp/mtThreadApp/userinfo").then(response => {
    //   console.log(response)
    // })
  },
};
</script>
