<template>
  <ConfigExampleCard>
    <ThemeButton/>
    <p>This is a boolean value example</p>
    <p>You can switch theme between light and dark</p>
  </ConfigExampleCard>
  <img alt="Vue logo" src="./assets/logo.png">
  <HelloWorld/>
</template>

<script>
import ConfigExampleCard from "@/components/ConfigExampleCard";
import ThemeButton from "@/components/ThemeButton";
import HelloWorld from './components/HelloWorld.vue'

export default {
  name: 'App',
  components: {
    ConfigExampleCard,
    ThemeButton,
    HelloWorld
  }
}
</script>

<style>
html, body {
  padding: 0;
  margin: 0;
  background-color: var(--background-color-primary);
}

#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: var(--text-primary-color);
  margin-top: 60px;
}
</style>
