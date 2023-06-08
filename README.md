# BaseProject

It is used to quick start a new projects

In the project is using
Navigation: Appyx
Cache: SQLDelight, DataStore
Network: Ktor
Serialize: Kotlin.Serialize
UI: Jetpack Compose
DI: Dagger2

The project has the structure:
- app
- auth
  - data
  - domain
  - ui
- core
  - data
  - domain
  - ui
- route 

The project is built on the principle of MVI architecture

The "auth" module is an example for other feature-modules

In the project use some of [my personal util libraries](https://github.com/Privatik/MyUtilsProject/tree/master/util)
For the state machine in the presenter use my personal library from [maven](https://central.sonatype.com/artifact/io.github.privatik/machine/1.0.2-beta) 
This library was inspired by the existing [library](https://github.com/dimsuz/unicorn/tree/master), but something has changed.
```
internal class AuthPresenter @Inject constructor(
    private val interactor: AuthInteractor
): Presenter<AuthState, AuthIntent, Effect>(
    initialState = AuthState()
) {

    override fun CoroutineScope.buildIntent(): AuthIntent = AuthIntent(this)

    override fun ReducerDSL<AuthState, Effect>.reducer() {
        onEach(
            intent.changeLogin.asFlow(),
            changeState = { oldState, payload ->
                oldState.copy(login = payload)
            }
        )

        onEach(
            intent.changePassword.asFlow(),
            changeState = { oldState, payload ->
                oldState.copy(password = payload)
            }
        )

        onEach(
            intent.doLogin.asFlow(),
            action = { _, newState , _ ->
                interactor.sinIn(newState.login, newState.password.toString())
            }
        )

        onEach(
            interactor.singInFlow,
            effect = { _, _, payload ->
                when (payload){
                    is StateModel.Content<String> -> {
                        DefaultEffectHandler.DefaultEffect.Navigate(Route.OpenNextScreen(payload.data))
                    }
                    is StateModel.Error -> {
                        DefaultEffectHandler.DefaultEffect.SnackBar(StringUI.Message("error"), DefaultEffectHandler.DefaultEffect.SnackBar.Type.Error)
                    }
                    StateModel.Loading,
                    StateModel.None -> { null }
                }
            },
        )
    }
}
```

For business logic use a base interactor for single state beetwen ui-data layers
```
internal class AuthInteractorImpl @Inject constructor(
    private val repository: AuthRepository
): BaseInteractor<State>(State()), AuthInteractor{

    override val handleDataFromOutSide: Flow<State> = merge(
        repository.singInFlow.updateState { state, payload ->
            state.copy(singIn = payload.map { it.email }.asStateModel())
        },
    )

    override suspend fun sinIn(login: String, password: String) = withContext(Dispatchers.IO) {
        updateState { state -> state.copy(singIn = StateModel.Loading) }
        repository.singIn(login, password)
    }

    override val singInFlow: Flow<StateModel<String>> by lazy {
        state { it.singIn }.distinctUntilChanged()
    }


}

internal data class State(
    val singIn: StateModel<String> = StateModel.None,
)
```

For the presenterStore used also my library from [maven](https://central.sonatype.com/artifact/io.github.privatik/presenter-android/1.0.2-beta) (the library is not tested enough, use the owl solution)


