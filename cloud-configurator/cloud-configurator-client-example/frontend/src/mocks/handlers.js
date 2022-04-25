import { rest } from 'msw'

export default [
    rest.get('/title', (req, res, ctx) => {
        return res(
            ctx.json({
                title: 'Spring Configurator Client Example'
            })
        )
    })
]