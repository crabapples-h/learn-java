<template>
    <div id="app">
        <keep-alive>
            <router-view/>
        </keep-alive>
    </div>
</template>
<script>
import storage from '@/store/storage'
import Loading from "./views/base/Loading";

export default {
    name: 'App',
    data() {
        return {}
    },
    created() {
        this.initMenus()
    },
    mounted() {
        window.addEventListener('beforeunload', () => {
            alert(1)
        })
    },
    methods: {
        beforeunload(e) {
            console.log(e)
        },
        initMenus() {
            const token = storage.getToken()
            this.$store.dispatch('INIT_TOKEN', token)
            this.$store.dispatch('MENUS')
            this.$store.dispatch('PERMISSIONS')
            this.$store.dispatch('USER_INFO')
        }
    }
}
</script>

<style>
#app {
    margin: 0;
    padding: 0;
}
</style>
