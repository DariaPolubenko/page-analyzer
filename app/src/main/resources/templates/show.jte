@import hexlet.code.utils.NamedRoutes
@import hexlet.code.dto.UrlPage
@param UrlPage page

@template.layout.page(

content = @`
    <div class="mb-3">
        <p>${page.getUrl().getId()}</p>
    </div>

    <div class="mb-3">
        <p>${page.getUrl().getName()}</p>
    </div>

    <div class="mb-3">
        <p>${page.getUrl().getCreatedAt().getTime()}</p>
    </div>
`,
)