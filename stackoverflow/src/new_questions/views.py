from django.shortcuts import render
from django.template import Context, loader
from django.http import HttpResponse

def show(request):
	name = "New questions"
	template = loader.get_template('index.html')
	context = Context({'name' : name})
	return HttpResponse(template.render(context))
