<template>
  <div class="card">
    <slot>
      <p>This is a config value example</p>
      <p>You can modify it using the configurator</p>
    </slot>
  </div>
</template>

<script>
export default {
  name: "ConfigExampleCard"
}
</script>

<style scoped>
.card {
  padding: 2rem 4rem;
  height: 200px;
  width: 300px;
  text-align: center;
  border: 1px solid var(--accent-color);
  border-radius: 4px;
  background-color: var(--background-color-secondary);
}
</style>