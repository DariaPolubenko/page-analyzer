@import hexlet.code.dto.UrlsPage
@import hexlet.code.utils.NamedRoutes
@param  UrlsPage page

@template.layout.page(
content = @`
    @if(page.getFlash() != null)
        <div class="alert alert-${page.getFlashType()}" role="alert">
            ${page.getFlash()}
        </div>
    @endif
    <div class="container">
        @if(page.getUrls().isEmpty())
            <p>Пока не добавлен ни один сайт</p>
        @else
            @for(var url : page.getUrls())
                <div>
                    <a href="${NamedRoutes.urlsPath(url.getId())}">${url.getName()}</a>
                </div>
            @endfor
        @endif
    </div>
    `
)
