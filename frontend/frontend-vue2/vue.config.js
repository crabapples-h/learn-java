// let url = global.setting && global.setting.baseUrl;
// let url = 'http://localhost:3300/';
let env = process.env.NODE_ENV;
const path = require('path')
console.log('启动环境:', env);

function resolve(dir) {
    return path.join(__dirname, dir)
}

module.exports = {
    runtimeCompiler: true,
    /** 区分打包环境与开发环境
     * process.env.NODE_ENV==='production'  (打包环境)
     * process.env.NODE_ENV==='development' (开发环境)
     * baseUrl: process.env.NODE_ENV==='production'?"https://cdn.didabisai.com/front/":'front/',
     */
    // 项目部署的基础路径
    // 我们默认假设你的应用将会部署在域名的根部,
    // 例如 https://www.my-app.com/
    // 如果你的应用部署在一个子路径下，那么你需要在这里
    // 指定子路径。比如将你的应用部署在
    // https://www.foobar.com/my-app/
    // 那么将这个值改为 '/my-app/'
    // baseUrl: "/",
    // 构建好的文件输出到哪里
    outputDir: "dist",
    // where to put static assets (js/css/img/font/...)
    // 放置生成的静态资源 (js、css、img、fonts) 的 (相对于 outputDir 的) 目录
    assetsDir: "",
    // 是否在保存时使用‘eslint-loader’进行检查
    // 有效值: true | false | 'error',当设置为‘error’时，检查出的错误会触发编译失败
    lintOnSave: false,
    // 使用带有浏览器内编译器的完整构建版本 // https://vuejs.org/v2/guide/installation.html#Runtime-Compiler-vs-Runtime-only
    // runtimeCompiler: false,
    // babel-loader默认会跳过`node_modules`依赖. // 通过这个选项可以显示转译一个依赖
    // transpileDependencies: [
    //     /* string or regex */
    // ],
    // 是否为生产环境构建生成sourceMap?
    productionSourceMap: false,
    // 调整内部的webpack配置. // see https://github.com/vuejs/vue-cli/blob/dev/docs/webpack.md
    // configureWebpack: () => {
    // },
    // parallel: require("os").cpus().length > 1,
    // PWA 插件相关配置 // see https://github.com/vuejs/vue-cli/tree/dev/packages/%40vue/cli-plugin-pwa
    // pwa: {},
    // configure webpack-dev-server behavior
    chainWebpack: (config) => {
        config.resolve.alias
            .set('@', resolve('src'))
            .set('@assets', resolve('src/assets'))
            .set('@comp', resolve('src/components'))
            .set('@public', resolve('public'))
    },
    css: {
        // 将组件内部的css提取到一个单独的css文件（只用在生产环境）
        // 也可以是传递给 extract-text-webpack-plugin 的选项对象
        // extract: true,
        // 允许生成 CSS source maps?
        sourceMap: false,
        loaderOptions: {
            less: {
                lessOptions: {
                    //If you are using less-loader@5 please spread the lessOptions to options directly
                    javascriptEnabled: true,
                    // modifyVars: {
                    //     /* less 变量覆盖，用于自定义 ant design 主题 */
                    //     'primary-color': '#2daef6',
                    //     'link-color': '#2daef6',
                    //     'border-radius-base': '4px',
                    // },
                }
            }
        }
    },
    pages: {
        index: {
            // page 的入口
            entry: 'src/main.js',
            // 模板来源
            template: 'public/index.html',
            // 在 dist/index.html 的输出
            filename: 'index.html',
            // 当使用 title 选项时，
            // template 中的 title 标签需要是 <title><%= htmlWebpackPlugin.options.title %></title>
            title: '管理系统',
            // 在这个页面中包含的块，默认情况下会包含
            // 提取出来的通用 chunk 和 vendor chunk。
            chunks: ['chunk-vendors', 'chunk-common', 'index']
        }
    },
    devServer: {
        hot: true,       // 开启 HMR
        liveReload: true, // 可选，自动刷新
        host: "0.0.0.0",
        port: 8080,
        https: false,
        proxy: {
            '/ws': {
                target: 'http://localhost:9093/', // 接口的域名
                ws: true,
                // secure: false,  // 如果是https接口，需要配置这个参数
                changeOrigin: true, // 如果接口跨域，需要进行这个参数配置
                // pathRewrite: {
                // 把 /api 开头的路径替换为 ''
                //     '^/api': ''
                // }
            },
            '/api': {
                target: 'http://localhost:9093/', // 接口的域名
                // secure: false,  // 如果是https接口，需要配置这个参数
                changeOrigin: true, // 如果接口跨域，需要进行这个参数配置
                // pathRewrite: {
                // 把 /api 开头的路径替换为 ''
                //     '^/api': ''
                // }
            },
            '/open-api': {
                target: 'http://localhost:80/', // 接口的域名
                // secure: false,  // 如果是https接口，需要配置这个参数
                changeOrigin: true, // 如果接口跨域，需要进行这个参数配置
                pathRewrite: {
                    // 把 /open-api 开头的路径替换为 '/open'
                    '^/open-api': '/open'
                }
            },
            '/file': {
                target: 'http://localhost:9093/', // 接口的域名
                // secure: false,  // 如果是https接口，需要配置这个参数
                changeOrigin: true, // 如果接口跨域，需要进行这个参数配置
            }
        },
        // string | Object
        // before: app => {}

    },
    // 第三方插件配置
    pluginOptions: {
        // "style-resources-loader": {
        //     preProcessor: "less",
        //     patterns: [
        //         //这个是加上自己的路径，
        //         //注意：试过不能使用别名路径
        //         path.resolve(__dirname, "public/color.less")
        //     ]
        // }
    },
};
