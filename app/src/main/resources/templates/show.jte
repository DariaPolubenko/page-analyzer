@import hexlet.code.utils.NamedRoutes
@import hexlet.code.dto.UrlPage
@import java.time.format.DateTimeFormatter
@param UrlPage page

!{var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");}

@template.layout.page(

content = @`
    <div class="mb-3">
        <p>${page.getUrl().getId()}</p>
    </div>

    <div class="mb-3">
        <p>${page.getUrl().getName()}</p>
    </div>

    <div class="mb-3">
        <p>${page.getUrl().getCreatedAt().toLocalDateTime().format(formatter)}</p>
    </div>
`,
)