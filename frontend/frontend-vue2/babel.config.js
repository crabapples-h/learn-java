module.exports = {
    presets: [
        '@vue/cli-plugin-babel/preset',
    ],
    plugins: [
        [
            // "import",
            // { libraryName: "ant-design-vue", libraryDirectory: "es", style: true },
            //配置路由懒加载插件
            "@babel/plugin-syntax-dynamic-import"
        ]
    ]
}
