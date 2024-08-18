@import hexlet.code.dto.UrlsPage
@param  UrlsPage page

@template.layout.page(
content = @`
<div>
    @if(page.getUrls().isEmpty())
        <p>Пока не добавлен ни один сайт</p>
    @else
        @for(var url : page.getUrls())
            <div>
                <p>${url.getName()}</p>
            </div>
        @endfor
    @endif
</div>
`
)
