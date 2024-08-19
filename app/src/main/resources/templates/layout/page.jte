@import hexlet.code.utils.NamedRoutes
@import hexlet.code.dto.BasePage
@import gg.jte.Content
@param Content content
@param BasePage page = null

<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
          crossorigin="anonymous">
    <title>Анализатор страниц</title>
</head>
<body class="container">
<div class="btn-group row">
    <p class="d-inline-flex gap-1">
        <a href="${NamedRoutes.mainPath()}" class="btn btn-outline-primary rounded-1 fw-light">Главная</a>
        <a href="${NamedRoutes.urlsPath()}" class="btn btn-outline-primary rounded-1 fw-light">Сайты</a>
    </p>
</div>
${content}
</body>
</html>