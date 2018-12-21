<!DOCTYPE html>
<html>
<head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>头条资讯 - 牛客网</title>
    <meta name="viewport" content="width=device-width, minimum-scale=1.0, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <meta name="keywords" content="读《Web 全栈工程师的自我修养》">
    <meta name="description" content="阅读影浅分享的读《Web 全栈工程师的自我修养》，就在牛客网。">

    <link rel="stylesheet" type="text/css" href="/styles/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/styles/font-awesome.min.css">

    <link rel="stylesheet" media="all" href="/styles/style.css">
    <script type="text/javascript" src="/scripts/jquery.js"></script>
    <script type="text/javascript" src="/scripts/main/base/base.js"></script>
    <script type="text/javascript" src="/scripts/main/base/util.js"></script>
    <script type="text/javascript" src="/scripts/main/base/event.js"></script>
    <script type="text/javascript" src="/scripts/main/base/upload.js"></script>
    <script type="text/javascript" src="/scripts/main/component/component.js"></script>
    <script type="text/javascript" src="/scripts/main/component/popup.js"></script>
    <script type="text/javascript" src="/scripts/main/component/popupLogin.js"></script>
    <script type="text/javascript" src="/scripts/main/component/upload.js"></script>
    <script type="text/javascript" src="/scripts/main/component/popupUpload.js"></script>
    <script type="text/javascript" src="/scripts/main/site/home.js"></script>

</head>
<body class="welcome_index">

<header class="navbar navbar-default navbar-static-top bs-docs-nav" id="top" role="banner">
    <div class="container">
        <div class="navbar-header">
            <button class="navbar-toggle collapsed" type="button" data-toggle="collapse" data-target=".bs-navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>

            <a href="/" class="navbar-brand logo">
                <h1>头条资讯</h1>
                <h3>你关心的才是头条</h3>
            </a>
        </div>

        <nav class="collapse navbar-collapse bs-navbar-collapse" role="navigation">

            <ul class="nav navbar-nav navbar-right">


                <li class="js-share"><a href="javascript:void(0);">分享</a></li>
                <li class=""><a href="/msg/list">站内信</a></li>
                   <#if user??>
                <li class="js-login"><a href="javascript:void(0);">${user.name}</a></li>
                   <#else>
                <li class="js-login"><a href="javascript:void(0);">登陆</a></li>
                   </#if>
            </ul>

        </nav>
    </div>
</header>