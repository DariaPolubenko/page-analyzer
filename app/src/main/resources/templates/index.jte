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
    <div class="container" style="margin-top: 2rem;">
        @if(page.getUrls().isEmpty())
            <p>Пока не добавлен ни один сайт</p>
        @else
            <table class="table">
            <thead>
            <tr>
                <th scope="col">ID</th>
                <th scope="col">Имя</th>
            </tr>
            </thead>
                <tbody>
                @for(var url : page.getUrls())
                    <tr>
                        <th scope="row">${url.getId()}</th>
                        <td><a href="${NamedRoutes.urlsPath(url.getId())}">${url.getName()}</a></td>
                    </tr>
                @endfor
                </tbody>
            </table>
        @endif
    </div>
    `
)
