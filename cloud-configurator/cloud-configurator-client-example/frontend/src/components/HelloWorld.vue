<template>
  <div class="hello">
    <h1>Welcome to {{title}}</h1>
    <p>
      You can modify welcome message without restarting Spring Boot application using
      <a href="todo" target="_blank">Spring Boot Configurator</a>. <!--TODO-->
    </p>
    <h3>Essential Links</h3>
    <ul>
      <li><a href="todo" target="_blank" rel="noopener">Docs</a></li> <!--TODO-->
      <li><a href="https://github.com/sadv1r/cloud/issues?q=is%3Aopen+is%3Aissue+label%3Aconfigurator" target="_blank" rel="noopener">Issue Tracker</a></li>
      <li><a href="https://github.com/sadv1r/cloud/spring-boot-configurator" target="_blank" rel="noopener">Source Code</a></li>
    </ul>
  </div>
</template>

<script>
export default {
  name: 'HelloWorld',
  data() {
    return {
      timer: '',
      title: '...loading'
    }
  },
  created() {
    this.fetchEventsList();
    this.timer = setInterval(this.fetchEventsList, 5000)
  },
  beforeUnmount() {
    this.cancelAutoUpdate()
  },
  methods: {
    fetchEventsList() {
      fetch('/title')
          .then(response => response.json())
          .then(json => {
            console.log(json)
            this.title = json.title
          })
    },
    cancelAutoUpdate() {
      clearInterval(this.timer);
    }
  }
}
</script>

<style scoped>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
